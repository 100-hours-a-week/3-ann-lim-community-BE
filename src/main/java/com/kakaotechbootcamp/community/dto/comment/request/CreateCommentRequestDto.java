package com.kakaotechbootcamp.community.dto.comment.request;

import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {

    @NotBlank(message = "{required.comment.content}", groups = RequiredGroup.class)
    private String content;
}
