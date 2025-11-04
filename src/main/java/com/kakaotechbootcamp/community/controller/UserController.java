package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.user.request.CreateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.request.UpdateUserPasswordRequestDto;
import com.kakaotechbootcamp.community.dto.user.request.UpdateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.CreateUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.EditUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.UpdateUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.UserProfileResponseDto;
import com.kakaotechbootcamp.community.service.UserService;
import com.kakaotechbootcamp.community.validation.ValidationOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getProfileImage(HttpServletRequest request) {

        UserProfileResponseDto response = userService.getProfileImage(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("프로필 사진 조회 성공")
                        .data(response)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> signup(
            @Validated(ValidationOrder.class) @RequestBody CreateUserRequestDto createUserRequest) {

        CreateUserResponseDto response =  userService.signup(createUserRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("회원가입 성공")
                        .data(response)
                        .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserForEdit(HttpServletRequest request, @PathVariable Long userId) {

        EditUserResponseDto response =  userService.getUserForEdit(request, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("회원 수정 정보 조회 성공")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUserInfo(
            HttpServletRequest request,
            @PathVariable Long userId,
            @Validated(ValidationOrder.class) @RequestBody UpdateUserRequestDto updateUserRequest) {

        UpdateUserResponseDto response =  userService.updateUserInfo(request, userId, updateUserRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("회원 정보 수정 성공")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<?>> updateUserPassword(
            HttpServletRequest request,
            @PathVariable Long userId,
            @Validated(ValidationOrder.class) @RequestBody UpdateUserPasswordRequestDto updateUserPasswordRequest) {

         userService.updateUserPassword(request, userId, updateUserPasswordRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("회원 비밀번호 수정 성공")
                        .data(null)
                        .build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(HttpServletRequest request, HttpServletResponse response,  @PathVariable Long userId) {

        userService.deleteUser(request, response, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("회원 탈퇴 성공")
                        .data(null)
                        .build());
    }
}
