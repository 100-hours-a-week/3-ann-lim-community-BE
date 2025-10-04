package com.kakaotechbootcamp.community.dto.comment.response;

import com.kakaotechbootcamp.community.dto.post.response.PostUserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentData {

    private CommentInfo comment;
    private PostUserInfo user;

    public static CommentData of(CommentInfo comment, PostUserInfo user) {
        return CommentData.builder()
                .comment(comment)
                .user(user)
                .build();
    }
}
