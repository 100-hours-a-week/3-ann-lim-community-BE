package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostData {

    private PostInfo post;
    private PostUserInfo user;
    private CountInfo counts;

    public static PostData of(PostInfo post, PostUserInfo user, CountInfo counts) {
        return PostData.builder()
                .post(post)
                .user(user)
                .counts(counts)
                .build();
    }
}
