package com.kakaotechbootcamp.community.dto.post.request;

import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostRequestDto {

    @Size(max = 26, message = "{max.post.title}", groups = LengthGroup.class)
    private String title;

    @NotBlank(message = "{required.post.title}", groups = RequiredGroup.class)
    private String content;

    private List<String> postImages;

    public boolean isAllFieldsNull() {
        return title == null && content == null && (postImages == null || postImages.isEmpty());
    }
}
