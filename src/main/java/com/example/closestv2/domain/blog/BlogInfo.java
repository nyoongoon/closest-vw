package com.example.closestv2.domain.blog;

import jakarta.persistence.Embeddable;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Embeddable
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode
final class BlogInfo {
    private @NotNull(message = URL_IS_REQUIRED) URL blogUrl;
    private @NotNull(message = BLOG_TITLE_IS_REQUIRED) String blogTitle;
    private @NotNull(message = BLOG_AUTHOR_IS_REQUIRED) String author;
    private @NotNull(message = BLOG_PUBLISHED_DATETIME_IS_REQUIRED) LocalDateTime publishedDateTime;
    private long blogVisitCount;
    private  String statusMessage;

    private BlogInfo(URL blogUrl, String blogTitle, String author, LocalDateTime publishedDateTime, long blogVisitCount, String statusMessage) {
        //        https://github.com/valid4j/valid4j
        if (Objects.isNull(blogUrl)) {
            throw new IllegalArgumentException(URL_IS_REQUIRED);
        }

        if (Objects.isNull(blogTitle)) {
            throw new IllegalArgumentException(BLOG_TITLE_IS_REQUIRED);
        }

        if (Objects.isNull(author)) {
            throw new IllegalArgumentException(BLOG_AUTHOR_IS_REQUIRED);
        }

        if (Objects.isNull(publishedDateTime)) {
            throw new IllegalArgumentException(BLOG_PUBLISHED_DATETIME_IS_REQUIRED);
        }

        this.blogUrl = blogUrl;
        this.blogTitle = blogTitle;
        this.author = author;
        this.publishedDateTime = publishedDateTime;
        this.blogVisitCount = blogVisitCount;
        this.statusMessage = statusMessage;
    }

    public @NotNull(message = URL_IS_REQUIRED) URL blogUrl() {
        return blogUrl;
    }

    public @NotNull(message = BLOG_TITLE_IS_REQUIRED) String blogTitle() {
        return blogTitle;
    }

    public @NotNull(message = BLOG_AUTHOR_IS_REQUIRED) String author() {
        return author;
    }

    public @NotNull(message = BLOG_PUBLISHED_DATETIME_IS_REQUIRED) LocalDateTime publishedDateTime() {
        return publishedDateTime;
    }

    public long blogVisitCount() {
        return blogVisitCount;
    }

    public String statusMessage() {
        return statusMessage;
    }

}
