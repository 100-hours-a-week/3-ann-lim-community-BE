package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.post.request.CreatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.response.CreatePostResponseDto;
import com.kakaotechbootcamp.community.dto.post.response.PostsResponseDto;
import com.kakaotechbootcamp.community.service.PostService;
import com.kakaotechbootcamp.community.validation.ValidationOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getPosts(@RequestParam(required = false) LocalDateTime lastPostCreatedAt, @RequestParam(required = false) Long lastPostId) {

        PostsResponseDto response = postService.getPosts(lastPostCreatedAt, lastPostId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("게시글 목록 조회 성공")
                        .data(response)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addPost(@Validated(ValidationOrder.class) @RequestBody CreatePostRequestDto createPostRequest) {

        CreatePostResponseDto response = postService.addPost(createPostRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("게시글 추가 성공")
                        .data(response)
                        .build());
    }
}
