package com.example.closestv2.domain.member;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;
import static com.example.closestv2.util.validation.Pattern.EMAIL;
import static com.example.closestv2.util.validation.Pattern.PASSWORD;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {

    @NotBlank(message = EMAIL_IS_REQUIRED)
    @Email(message = NOT_VALID_EMAIL)
    String userEmail;

    @NotBlank(message = PASSWORD_IS_REQUIRED)
    @Pattern(regexp = PASSWORD, message = NOT_VALID_PASSWORD)
    String password;

    @NotBlank(message = NICK_NAME_IS_REQUIRED)
    String nickName;

    @Builder(access = AccessLevel.PROTECTED)
    private MemberInfo(
            String userEmail,
            String password,
            String nickName
    ) {
        Assert.hasText(userEmail, EMAIL_IS_REQUIRED);
        Assert.isTrue(userEmail.matches(EMAIL), NOT_VALID_EMAIL);
        Assert.hasText(password, PASSWORD_IS_REQUIRED);
        Assert.isTrue(password.matches(PASSWORD), NOT_VALID_PASSWORD);
        Assert.hasText(password, PASSWORD_IS_REQUIRED);
        Assert.hasText(nickName, NICK_NAME_IS_REQUIRED);

        this.userEmail = userEmail;
        this.password = password;
        this.nickName = nickName;
    }
}
