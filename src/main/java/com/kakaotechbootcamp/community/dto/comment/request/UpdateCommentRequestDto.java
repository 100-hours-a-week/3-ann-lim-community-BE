package com.kakaotechbootcamp.community.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentRequestDto {

    private String content;

    public boolean isContentInvalid() {
        return content == null || content.trim().isEmpty();
    }
}
