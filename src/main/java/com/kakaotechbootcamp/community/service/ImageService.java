package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.image.response.CreateImageUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService {

    //Todo: AWS 학습 후 진행
    public CreateImageUrlResponseDto uploadImage(List<MultipartFile> files) {

        return null;
    }
}
