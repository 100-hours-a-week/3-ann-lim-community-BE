package com.kakaotechbootcamp.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호를 확인해주세요."),
    MISMATCH_PASSWORD(HttpStatus.UNPROCESSABLE_ENTITY, "비밀번호가 다릅니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다.");

    private final HttpStatus status;
    private final String message;
}
