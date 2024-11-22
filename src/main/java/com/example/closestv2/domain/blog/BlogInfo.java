package com.example.closestv2.domain.blog;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BlogInfo {
    @NotNull(message = URL_IS_REQUIRED)
    private URL blogUrl;

    @NotBlank(message = BLOG_TITLE_IS_REQUIRED)
    private String blogTitle;

    @NotBlank(message = BLOG_AUTHOR_IS_REQUIRED)
    private String author;

    @NotNull(message = BLOG_PUBLISHED_DATETIME_IS_REQUIRED)
    private LocalDateTime publishedDateTime;

    @NotNull(message = BLOG_VISIT_COUNT_IS_REQUIRED)
    private long blogVisitCount;

    private String statusMessage;

    @Builder(access = AccessLevel.PROTECTED)
    private BlogInfo(URL blogUrl,
                     String blogTitle,
                     String author,
                     LocalDateTime publishedDateTime,
                     long blogVisitCount,
                     String statusMessage
    ) {
        Assert.notNull(blogUrl, URL_IS_REQUIRED);
        Assert.hasText(blogTitle, BLOG_TITLE_IS_REQUIRED);
        Assert.hasText(author, BLOG_AUTHOR_IS_REQUIRED);
        Assert.notNull(publishedDateTime, BLOG_PUBLISHED_DATETIME_IS_REQUIRED);

        this.blogUrl = blogUrl;
        this.blogTitle = blogTitle;
        this.author = author;
        this.publishedDateTime = publishedDateTime;
        this.blogVisitCount = blogVisitCount;
        this.statusMessage = statusMessage;
    }
}
