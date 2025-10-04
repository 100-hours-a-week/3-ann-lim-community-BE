package com.kakaotechbootcamp.community.dto.post.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostRequestDto {

    @NotBlank
    @Size(max = 26)
    private String title;

    @NotBlank
    private String content;

    private List<String> postImages;
}
