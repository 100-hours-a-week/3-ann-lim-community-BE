package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.image.response.CreateImagePresignedUrlResponseDto;
import com.kakaotechbootcamp.community.dto.image.response.CreateImageUrlResponseDto;
import com.kakaotechbootcamp.community.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Todo: AWS 학습 후 진행
@Service
public class ImageService {

    public CreateImagePresignedUrlResponseDto generatePresignedUrl(MultipartFile file) {
        return new CreateImagePresignedUrlResponseDto("https://picsum.photos/200?0");
    }

    public CreateImageUrlResponseDto uploadImage(List<MultipartFile> files) {

        List<String> images = new ArrayList<>();
        images.add("https://picsum.photos/200?1");

        return new CreateImageUrlResponseDto(images);
    }
}
