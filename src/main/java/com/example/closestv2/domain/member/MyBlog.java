package com.example.closestv2.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MY_BLOG_URL_IS_REQUIRED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyBlog {
    @NotNull(message = MY_BLOG_URL_IS_REQUIRED)
    @Column(unique = true)
    URL blogUrl;

    long myBlogVisitCount;

    String statusMessage;

    @Builder(access = AccessLevel.PROTECTED)
    public MyBlog(
            URL blogUrl,
            long myBlogVisitCount,
            String statusMessage
    ) {
        Assert.notNull(blogUrl, MY_BLOG_URL_IS_REQUIRED);

        this.blogUrl = blogUrl;
        this.myBlogVisitCount = myBlogVisitCount;
        this.statusMessage = statusMessage;
    }
}
