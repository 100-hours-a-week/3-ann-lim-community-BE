package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EditPostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private List<ImageInfo> images;

    public static EditPostResponseDto of(Long postId, String title, String content, List<ImageInfo> images) {
        return EditPostResponseDto.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .images(images)
                .build();
    }
}
