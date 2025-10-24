package com.kakaotechbootcamp.community.dto.post.request;

import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostRequestDto {

    @Size(max = 26, message = "{max.post.title}", groups = LengthGroup.class)
    private String title;

    private String content;

    private List<ImageMetaInfo> postImages;

    public boolean isAllFieldsNull() {
        return title == null && content == null && (postImages == null || postImages.isEmpty());
    }

    public boolean isTitleEmpty() {
        return title.trim().isEmpty();
    }

    public boolean isContentEmpty() {
        return content.trim().isEmpty();
    }
}
