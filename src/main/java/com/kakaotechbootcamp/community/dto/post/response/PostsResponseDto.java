package com.kakaotechbootcamp.community.dto.post.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostsResponseDto {

    private List<PostSummaryResponseDto> posts;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastPostCreatedAt;

    private Long lastPostId;

    public static PostsResponseDto of (
            List<PostSummaryResponseDto> posts, LocalDateTime lastPostCreatedAt, Long lastPostId) {
        return PostsResponseDto.builder()
                .posts(posts)
                .lastPostCreatedAt(lastPostCreatedAt)
                .lastPostId(lastPostId)
                .build();
    }
}
