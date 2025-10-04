package com.kakaotechbootcamp.community.dto.comment.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUserInfo {

    private Long userId;
    private String nickname;
    private String profileImage;

    public static CommentUserInfo of(Long userId, String nickname, String profileImage) {
        return CommentUserInfo.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
