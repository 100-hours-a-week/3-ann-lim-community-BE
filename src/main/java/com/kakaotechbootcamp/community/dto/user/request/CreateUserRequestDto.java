package com.kakaotechbootcamp.community.dto.user.request;

import com.kakaotechbootcamp.community.validation.group.FormatGroup;
import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequestDto {

    @NotBlank(message = "{required.user.email}", groups = RequiredGroup.class)
    @Email(message = "{invalid.user.email}", groups = FormatGroup.class)
    @Pattern(
            regexp = "^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)[A-Za-z0-9._%+-]+@([A-Za-z0-9]+(-[A-Za-z0-9]+)*\\.)+[A-Za-z]{2,}$",
            message = "{invalid.user.email}", groups = FormatGroup.class
    )
    @Size(min = 6, message = "{invalid.user.email}", groups = LengthGroup.class)
    private String email;

    @NotBlank(message = "{required.user.password}", groups = RequiredGroup.class)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[^\\s]{8,20}$",
            message = "{invalid.user.password}", groups = FormatGroup.class)
    private String password;

    @NotBlank(message = "{required.user.passwordConfirm}", groups = RequiredGroup.class)
    private String passwordConfirm;

    @NotBlank(message = "{required.user.nickname}", groups = RequiredGroup.class)
    @Pattern(regexp = "^(?!.*\\s).+$", message = "{invalid.user.nickname}", groups = FormatGroup.class)
    @Size(max = 10, message = "{max.user.nickname}", groups = LengthGroup.class)
    private String nickname;

    private String profileImage;
}
