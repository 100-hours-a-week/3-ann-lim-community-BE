package com.kakaotechbootcamp.community.dto.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentRequestDto {

    private String content;

    public boolean isContentNull() {
        return content == null;
    }
}
