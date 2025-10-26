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
        images.add("https://picsum.photos/200?0");

        return new CreateImageUrlResponseDto(images);
    }
}
