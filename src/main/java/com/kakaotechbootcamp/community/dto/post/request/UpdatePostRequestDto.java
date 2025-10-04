package com.kakaotechbootcamp.community.dto.post.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostRequestDto {

    @Size(max = 26)
    private String title;

    private String content;

    private List<String> postImages;
}
