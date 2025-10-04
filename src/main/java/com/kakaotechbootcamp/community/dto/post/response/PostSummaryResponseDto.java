package com.kakaotechbootcamp.community.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostSummaryResponseDto {

    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String nickname;
    private String profileImage;
    private Long likeCount;
    private Long commentCount;
    private Long viewCount;
}
