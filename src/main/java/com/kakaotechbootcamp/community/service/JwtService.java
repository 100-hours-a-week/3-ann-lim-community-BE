package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.token.TokenPairDto;
import com.kakaotechbootcamp.community.dto.token.TokenResponseDto;
import com.kakaotechbootcamp.community.entity.RefreshToken;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.RefreshTokenRepository;
import com.kakaotechbootcamp.community.repository.UserRepository;
import com.kakaotechbootcamp.community.jwt.JwtProvider;
import com.kakaotechbootcamp.community.jwt.JwtTokenExtractor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtTokenExtractor jwtTokenExtractor;

    private static final int ACCESS_TOKEN_EXPIRATION = 15 * 60;
    private static final int REFRESH_TOKEN_EXPIRATION = 14 * 24 * 3600;

    public TokenResponseDto reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtTokenExtractor.extractRefreshToken(request)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        var parsedRefreshToken = jwtProvider.parse(refreshToken);

        RefreshToken refreshTokenInfo = refreshTokenRepository.findByRefreshTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        if (refreshTokenInfo.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long userId = Long.valueOf(parsedRefreshToken.getBody().getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtProvider.createAccessToken(user.getId());
        addRefreshTokenCookie(response, "refresh_token", refreshToken, REFRESH_TOKEN_EXPIRATION);

        return new TokenResponseDto(newAccessToken);
    }

    public TokenPairDto generateAndSaveTokens(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        RefreshToken refreshTokenEntity = new RefreshToken(user, refreshToken, LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION));
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenPairDto(accessToken, refreshToken);
    }

    public TokenResponseDto deleteTokens(HttpServletRequest request, HttpServletResponse response) {

        Long userId = (Long) request.getAttribute("userId");
        String deletedAccessToken = jwtProvider.deleteAccessToken(userId);

        String refreshToken = jwtTokenExtractor.extractRefreshToken(request)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        refreshTokenEntity.revoke();

        addRefreshTokenCookie(response, "refresh_token", refreshToken, 0);

        return new TokenResponseDto(deletedAccessToken);
    }

//    private void addTokenCookies(HttpServletResponse response, TokenPairDto tokenPairDto) {
//        addRefreshTokenCookie(response, "refresh_token", tokenPairDto.getRefreshToken(), REFRESH_TOKEN_EXPIRATION);
//    }

    public void addRefreshTokenCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
