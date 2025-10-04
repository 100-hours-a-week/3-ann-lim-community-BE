package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EditPostResponseDto {

    private PostInfo post;
    private List<ImageInfo> images;

    public static EditPostResponseDto of(PostInfo post, List<ImageInfo> images) {
        return EditPostResponseDto.builder()
                .post(post)
                .images(images)
                .build();
    }
}
