package com.kakaotechbootcamp.community.dto.comment.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentsResponseDto {

    private List<CommentData> comments;
    private Long nextCursor;
    private boolean hasMore;

    public static CommentsResponseDto of (
            List<CommentData> comments, Long nextCursor, boolean hasMore) {
        return CommentsResponseDto.builder()
                .comments(comments)
                .nextCursor(nextCursor)
                .hasMore(hasMore)
                .build();
    }
}
