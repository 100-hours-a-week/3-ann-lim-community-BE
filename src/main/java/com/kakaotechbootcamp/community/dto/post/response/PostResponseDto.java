package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private PostInfo post;
    private List<ImageInfo> images;
    private PostUserInfo user;
    private CountInfo counts;

    public static PostResponseDto of(PostInfo post, PostUserInfo user, CountInfo counts) {
        return PostResponseDto.builder()
                .post(post)
                .user(user)
                .counts(counts)
                .build();
    }
}
