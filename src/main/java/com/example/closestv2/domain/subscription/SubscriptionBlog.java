package com.example.closestv2.domain.subscription;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Embeddable
@Builder(access = AccessLevel.PROTECTED)
record SubscriptionBlog(
        @NotNull(message = BLOG_URL_IS_REQUIRED)
        URL blogUrl,

        @NotBlank(message = BLOG_TITLE_IS_REQUIRED)
        String blogTitle,

        @NotNull(message = SUBSCRIPTION_PUBLISHED_DATETIME_IS_REQUIRED)
        LocalDateTime publishedDateTime,

        @NotNull(message = NEW_POST_COUNT_IS_REQUIRED)
        Long newPostCount
) {
}
