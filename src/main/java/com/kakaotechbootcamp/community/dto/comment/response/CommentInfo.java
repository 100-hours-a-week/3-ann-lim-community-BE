package com.kakaotechbootcamp.community.dto.comment.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentInfo {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentInfo of(
            Long commentId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return CommentInfo.builder()
                .commentId(commentId)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
