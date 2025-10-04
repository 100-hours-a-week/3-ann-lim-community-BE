package com.kakaotechbootcamp.community.dto.image.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateImageUrlResponseDto {
    private List<String> images;
}
