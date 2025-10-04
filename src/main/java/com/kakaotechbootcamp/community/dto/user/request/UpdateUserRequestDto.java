package com.kakaotechbootcamp.community.dto.user.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {

    @Pattern(regexp = "^(?!.*\\s).*$")
    @Size(max = 10)
    private String nickname;

    private String profileImage;
}
