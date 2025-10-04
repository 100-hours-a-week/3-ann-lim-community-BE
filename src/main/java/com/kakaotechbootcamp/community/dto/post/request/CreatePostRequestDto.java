package com.kakaotechbootcamp.community.dto.post.request;

import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostRequestDto {

    @NotBlank(message = "{required.post.title}", groups = RequiredGroup.class)
    @Size(max = 26, message = "{max.post.title}", groups = LengthGroup.class)
    private String title;

    @NotBlank(message = "{required.post.title}", groups = RequiredGroup.class)
    private String content;

    private List<String> postImages;
}
