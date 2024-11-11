package com.example.closestv2.domain.member;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.URL_IS_REQUIRED;

@Builder(access = AccessLevel.PROTECTED)
@Embeddable
public record MyBlog(
        @NotNull(message = URL_IS_REQUIRED)
        URL url,
        String statusMessage
) {
}
