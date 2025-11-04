package com.kakaotechbootcamp.community.controller;

import com.kakaotechbootcamp.community.dto.global.response.ApiResponse;
import com.kakaotechbootcamp.community.dto.image.response.CreateImagePresignedUrlResponseDto;
import com.kakaotechbootcamp.community.dto.image.response.CreateImageUrlResponseDto;
import com.kakaotechbootcamp.community.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Todo: 서비스 완료 후 진행
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/presigned-url", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> getPresignedUrl(@RequestPart("file") MultipartFile file) {
        CreateImagePresignedUrlResponseDto response = imageService.generatePresignedUrl(file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("이미지 업로드 성공")
                        .data(response)
                        .build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> uploadImage(@RequestPart("files") List<MultipartFile> files) {
        CreateImageUrlResponseDto response = imageService.uploadImage(files);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("이미지 업로드 성공")
                        .data(response)
                        .build());
    }
}
