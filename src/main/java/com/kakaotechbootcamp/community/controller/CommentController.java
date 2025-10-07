package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.comment.request.CreateCommentRequestDto;
import com.kakaotechbootcamp.community.dto.comment.request.UpdateCommentRequestDto;
import com.kakaotechbootcamp.community.dto.comment.response.CommentsResponseDto;
import com.kakaotechbootcamp.community.dto.comment.response.CreateCommentResponseDto;
import com.kakaotechbootcamp.community.dto.comment.response.UpdateCommentResponseDto;
import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.service.CommentService;
import com.kakaotechbootcamp.community.validation.ValidationOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getComments(
            @PathVariable Long postId,
            @RequestParam(required = false) LocalDateTime lastCommentCreatedAt,
            @RequestParam(required = false) Long lastCommentId) {

        CommentsResponseDto response = commentService.getComments(postId, lastCommentCreatedAt, lastCommentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("댓글 목록 조회 성공")
                        .data(response)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addComment(
            @PathVariable Long postId,
            @Validated(ValidationOrder.class) @RequestBody CreateCommentRequestDto createCommentRequest) {

        CreateCommentResponseDto response = commentService.addComment(postId, createCommentRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("댓글 추가 성공")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto updateCommentRequest) {

        UpdateCommentResponseDto response = commentService.updateComment(postId, commentId, updateCommentRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("댓글 수정 성공")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {

        commentService.deleteComment(postId, commentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("댓글 삭제 성공")
                        .data(null)
                        .build());
    }
}
