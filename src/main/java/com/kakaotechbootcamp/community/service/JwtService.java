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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtTokenExtractor jwtTokenExtractor;

    private static final int REFRESH_TOKEN_EXPIRATION = 14 * 24 * 3600;

    public TokenResponseDto reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtTokenExtractor.extractRefreshToken(request)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        var parsedRefreshToken = jwtProvider.parse(refreshToken);

        RefreshToken refreshTokenInfo = refreshTokenRepository.findByRefreshTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        if (refreshTokenInfo.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        Long userId = Long.valueOf(parsedRefreshToken.getBody().getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtProvider.createAccessToken(user.getId());
//        addRefreshTokenCookie(response, "refresh_token", refreshToken, REFRESH_TOKEN_EXPIRATION);

        return new TokenResponseDto(newAccessToken);
    }

    public TokenPairDto generateAndSaveTokens(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        RefreshToken refreshTokenEntity = new RefreshToken(user, refreshToken, LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION));
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenPairDto(accessToken, refreshToken);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .secure(false)
                .sameSite("None")
                .maxAge(maxAge)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void revokeRefreshToken(HttpServletRequest request) {

        String refreshToken = jwtTokenExtractor.extractRefreshToken(request)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        refreshTokenEntity.revoke();
    }

    public void expireRefreshToken(HttpServletRequest request, HttpServletResponse response) {

        Long userId = Long.valueOf(request.getAttribute("userId").toString());

        String deletedRefreshToken = jwtProvider.deleteRefreshToken(userId);
        addRefreshTokenCookie(response, "refresh_token", deletedRefreshToken, 0);
    }
}