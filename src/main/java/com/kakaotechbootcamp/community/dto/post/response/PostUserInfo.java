package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUserInfo {

    private Long userId;
    private String nickname;
    private String profileImage;

    public static PostUserInfo of(Long userId, String nickname, String profileImage) {
        return PostUserInfo.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
