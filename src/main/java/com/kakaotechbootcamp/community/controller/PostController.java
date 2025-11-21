package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.post.request.CreatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.request.UpdatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.response.*;
import com.kakaotechbootcamp.community.dto.postlike.response.CreateLikeResponseDto;
import com.kakaotechbootcamp.community.service.PostService;
import com.kakaotechbootcamp.community.validation.ValidationOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts")
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
    public ResponseEntity<ApiResponse<?>> addPost(HttpServletRequest request,  @Validated(ValidationOrder.class) @RequestBody CreatePostRequestDto createPostRequest) {

        CreatePostResponseDto response = postService.addPost(request, createPostRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("게시글 추가 성공")
                        .data(response)
                        .build());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> getPost(HttpServletRequest request, @PathVariable Long postId) {

        PostResponseDto response = postService.getPost(request, postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("게시글 조회 성공")
                        .data(response)
                        .build());
    }

    @GetMapping("/{postId}/edit")
    public ResponseEntity<ApiResponse<?>> getPostForEdit(HttpServletRequest request, @PathVariable Long postId) {

        EditPostResponseDto response = postService.getPostForEdit(request, postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("게시글 수정 정보 조회 성공")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> updatePost(HttpServletRequest request, @PathVariable Long postId, @Validated(ValidationOrder.class) @RequestBody UpdatePostRequestDto updatePostRequest) {

        UpdatePostResponseDto response = postService.updatePost(request, postId, updatePostRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("게시글 수정 성공")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> deletePost(HttpServletRequest request, @PathVariable Long postId) {

        postService.deletePost(request, postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("게시글 삭제 성공")
                        .data(null)
                        .build());
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<?>> addLike(HttpServletRequest request, @PathVariable Long postId) {

        CreateLikeResponseDto response = postService.addLike(request, postId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("좋아요 추가 성공")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<?>> deleteLike(HttpServletRequest request, @PathVariable Long postId) {

        postService.deleteLike(request, postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("좋아요 삭제 성공")
                        .data(null)
                        .build());
    }
}