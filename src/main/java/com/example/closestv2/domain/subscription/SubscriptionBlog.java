package com.example.closestv2.domain.subscription;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionBlog {
    @NotNull(message = BLOG_URL_IS_REQUIRED)
    URL blogUrl;

    @NotBlank(message = BLOG_TITLE_IS_REQUIRED)
    String blogTitle;

    @NotNull(message = SUBSCRIPTION_PUBLISHED_DATETIME_IS_REQUIRED)
    LocalDateTime publishedDateTime;

    @NotNull(message = NEW_POST_COUNT_IS_REQUIRED)
    Long newPostCount;

    @Builder(access = AccessLevel.PROTECTED)
    private SubscriptionBlog(
            URL blogUrl,
            String blogTitle,
            LocalDateTime publishedDateTime,
            Long newPostCount
    ) {
        Assert.notNull(blogUrl, BLOG_URL_IS_REQUIRED);
        Assert.hasText(blogTitle, BLOG_TITLE_IS_REQUIRED);
        Assert.notNull(publishedDateTime, SUBSCRIPTION_PUBLISHED_DATETIME_IS_REQUIRED);
        Assert.notNull(newPostCount, NEW_POST_COUNT_IS_REQUIRED);

        this.blogUrl = blogUrl;
        this.blogTitle = blogTitle;
        this.publishedDateTime = publishedDateTime;
        this.newPostCount = newPostCount;
    }
}
