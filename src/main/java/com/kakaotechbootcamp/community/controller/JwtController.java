package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.token.TokenResponseDto;
import com.kakaotechbootcamp.community.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/refresh")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> refresh(HttpServletRequest req, HttpServletResponse res) {

        TokenResponseDto response = jwtService.reissueAccessToken(req, res);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("토큰 재발급 성공")
                        .data(response)
                        .build());
    }
}
