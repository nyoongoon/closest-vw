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
record BlogInfo(
        @NotNull(message = URL_IS_REQUIRED)
        URL blogUrl,

        @NotNull(message = BLOG_TITLE_IS_REQUIRED)
        String blogTitle,

        @NotNull(message = BLOG_AUTHOR_IS_REQUIRED)
        String author,

        @NotNull(message = BLOG_PUBLISHED_DATETIME_IS_REQUIRED)
        LocalDateTime publishedDateTime,

        @NotNull(message = BLOG_VISIT_COUNT_IS_REQUIRED)
        Long blogVisitCount,

        String statusMessage
) {
}
