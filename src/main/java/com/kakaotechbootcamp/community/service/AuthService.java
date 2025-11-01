package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.token.TokenPairDto;
import com.kakaotechbootcamp.community.dto.token.TokenResponseDto;
import com.kakaotechbootcamp.community.dto.user.request.UserLoginRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.UserLoginResponseDto;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserLoginResponseDto login(HttpServletResponse response, UserLoginRequestDto loginRequest) {

        int refreshTtlSec = 14 * 24 * 3600;

        User user = userRepository.findByEmailAndDeletedAtIsNull(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        TokenPairDto tokenPairDto = jwtService.generateAndSaveTokens(user);
        jwtService.addRefreshTokenCookie(response, "refresh_token", tokenPairDto.getRefreshToken(), refreshTtlSec);

        return UserLoginResponseDto.of(tokenPairDto.getAccessToken(), user.getId());
    }

    public TokenResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        return jwtService.deleteTokens(request, response);
    }
}
