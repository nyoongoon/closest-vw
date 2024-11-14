package com.example.closestv2.domain.member;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;
import static com.example.closestv2.util.validation.Pattern.PASSWORD;

@Embeddable
@Builder(access = AccessLevel.PROTECTED)
record MemberInfo(
        @Email
        @NotBlank(message = EMAIL_IS_REQUIRED)
        String userEmail,

        @Pattern(regexp = PASSWORD, message = NOT_VALID_PASSWORD)
        @NotBlank(message = PASSWORD_IS_REQUIRED)
        String password,

        @NotBlank
        String nickName
) {
}
