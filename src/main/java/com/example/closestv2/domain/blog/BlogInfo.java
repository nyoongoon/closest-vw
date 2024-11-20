package com.example.closestv2.domain.blog;

import jakarta.persistence.Embeddable;
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
public class BlogInfo {

    private URL blogUrl;

    private String blogTitle;

    private String author;

    private LocalDateTime publishedDateTime;

    private Long blogVisitCount;

    private String statusMessage;

    @Builder(access = AccessLevel.PROTECTED)
    private BlogInfo(URL blogUrl,
                       String blogTitle,
                       String author,
                       LocalDateTime publishedDateTime,
                       Long blogVisitCount,
                       String statusMessage
    ) {
        Assert.notNull(blogUrl, URL_IS_REQUIRED);
        Assert.hasText(blogTitle, BLOG_TITLE_IS_REQUIRED);
        Assert.hasText(author, BLOG_AUTHOR_IS_REQUIRED);
        Assert.notNull(publishedDateTime, BLOG_PUBLISHED_DATETIME_IS_REQUIRED);
        Assert.notNull(blogVisitCount, BLOG_VISIT_COUNT_IS_REQUIRED);

        this.blogUrl = blogUrl;
        this.blogTitle = blogTitle;
        this.author = author;
        this.publishedDateTime = publishedDateTime;
        this.blogVisitCount = blogVisitCount;
        this.statusMessage = statusMessage;
    }
}
