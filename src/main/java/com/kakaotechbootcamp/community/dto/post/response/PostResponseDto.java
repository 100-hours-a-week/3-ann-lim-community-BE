package com.kakaotechbootcamp.community.dto.post.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private List<ImageDisplayInfo> images;

    private Long likeCount;
    private Long viewCount;
    private Long commentCount;

    private boolean isLiked;

    private Long userId;
    private String nickname;
    private String profileImage;

    public static PostResponseDto of(Long postId, String title, String content,
                                     LocalDateTime createdAt, LocalDateTime updatedAt,
                                     List<ImageDisplayInfo> images,
                                     Long likeCount, Long viewCount, Long commentCount,
                                     boolean isLiked,
                                     Long userId, String nickname, String profileImage) {
        return PostResponseDto.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .images(images)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .isLiked(isLiked)
                .userId(userId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
