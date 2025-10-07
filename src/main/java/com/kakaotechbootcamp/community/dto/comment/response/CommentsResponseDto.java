package com.kakaotechbootcamp.community.dto.comment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentsResponseDto {

    List<CommentSummaryResponseDto> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastCommentCreatedAt;

    private Long lastCommentId;

    public static CommentsResponseDto of (
            List<CommentSummaryResponseDto> comments, LocalDateTime lastCommentCreatedAt, Long lastCommentId) {
        return CommentsResponseDto.builder()
                .comments(comments)
                .lastCommentCreatedAt(lastCommentCreatedAt)
                .lastCommentId(lastCommentId)
                .build();
    }
}
