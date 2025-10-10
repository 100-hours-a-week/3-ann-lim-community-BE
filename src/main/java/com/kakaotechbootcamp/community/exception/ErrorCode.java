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
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    POST_COUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글의 통계 정보를 찾을 수 없습니다."),
    POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글의 좋아요를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "요청 본문이 비어있습니다."),
    NO_UPDATE_CONTENT(HttpStatus.UNPROCESSABLE_ENTITY, "수정할 내용이 없습니다."),
    INVALID_TITLE(HttpStatus.UNPROCESSABLE_ENTITY, "제목은 공백일 수 없습니다."),
    INVALID_CONTENT(HttpStatus.UNPROCESSABLE_ENTITY, "내용은 공백일 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
