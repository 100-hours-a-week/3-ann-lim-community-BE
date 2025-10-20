package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.image.response.CreateImageUrlResponseDto;
import com.kakaotechbootcamp.community.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    //Todo: AWS 학습 후 진행
    public CreateImageUrlResponseDto uploadImage(List<MultipartFile> files) {

        List<String> images = new ArrayList<>();
        images.add("https://s3.ap-northeast-2.amazonaws.com/mybucket/default-profile.png");

        return new CreateImageUrlResponseDto(images);
    }
}
