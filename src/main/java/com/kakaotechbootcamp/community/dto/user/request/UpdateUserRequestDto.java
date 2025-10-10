package com.kakaotechbootcamp.community.dto.user.request;

import com.kakaotechbootcamp.community.validation.group.FormatGroup;
import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {

    @Pattern(regexp = "^(?!.*\\s).*$", message = "{invalid.user.nickname}", groups = FormatGroup.class)
    @Size(max = 10, message = "{max.user.nickname}", groups = LengthGroup.class)
    private String nickname;

    private String profileImage;

    public boolean isAllFieldsNull() {
        return nickname == null && profileImage == null;
    }
}
