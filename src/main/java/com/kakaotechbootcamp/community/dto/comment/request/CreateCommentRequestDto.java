package com.kakaotechbootcamp.community.dto.comment.request;

import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentRequestDto {

    @NotBlank(message = "{required.comment.content}", groups = RequiredGroup.class)
    private String content;
}
