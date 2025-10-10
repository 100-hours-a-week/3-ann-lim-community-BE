package com.kakaotechbootcamp.community.dto.user.request;

import com.kakaotechbootcamp.community.validation.group.FormatGroup;
import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserPasswordRequestDto {

    @NotBlank(message = "{required.user.password}", groups = RequiredGroup.class)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$",
            message = "{invalid.user.password}", groups = FormatGroup.class)
    private String password;

    @NotBlank(message = "{required.user.passwordConfirm}", groups = RequiredGroup.class)
    private String passwordConfirm;
}
