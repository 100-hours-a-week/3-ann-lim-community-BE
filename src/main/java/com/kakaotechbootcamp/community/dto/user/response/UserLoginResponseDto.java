package com.kakaotechbootcamp.community.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private String accessToken;
    private Long userId;

    public static UserLoginResponseDto of(String accessToken, Long userId) {
        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .userId(userId)
                .build();
    }
}