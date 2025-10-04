package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.post.response.PostsResponseDto;
import com.kakaotechbootcamp.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
