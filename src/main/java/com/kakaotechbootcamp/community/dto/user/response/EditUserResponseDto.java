package com.kakaotechbootcamp.community.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserResponseDto {

    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;

    public static EditUserResponseDto of(Long userId, String email, String nickname, String profileImage) {
        return EditUserResponseDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
