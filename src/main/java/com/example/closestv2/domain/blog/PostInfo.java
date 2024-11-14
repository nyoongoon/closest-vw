package com.example.closestv2.domain.blog;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Embeddable
@Builder(access = AccessLevel.PROTECTED)
record PostInfo(
        @NotNull(message = POST_URL_IS_REQUIRED)
        URL postUrl,

        @NotNull(message = POST_TITLE_IS_REQUIRED)
        String postTitle,

        @NotNull(message = POST_PUBLISHED_DATETIME_IS_REQUIRED)
        LocalDateTime publishedDateTime,

        @NotNull(message = POST_VISIT_COUNT_IS_REQUIRED)
        Long postVisitCount
) {
}
