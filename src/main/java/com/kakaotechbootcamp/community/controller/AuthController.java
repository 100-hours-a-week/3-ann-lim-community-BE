package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.user.request.UserLoginRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.UserLoginResponseDto;
import com.kakaotechbootcamp.community.service.AuthService;
import com.kakaotechbootcamp.community.validation.ValidationOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> login(
            @Validated(ValidationOrder.class) @RequestBody UserLoginRequestDto loginRequest) {

        UserLoginResponseDto response = authService.login(loginRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("로그인 성공")
                        .data(response)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> logout() {

        authService.logout();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("로그아웃 성공")
                        .data(null)
                        .build());
    }
}
