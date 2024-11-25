package com.example.closestv2.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MY_BLOG_VISIT_COUNT_IS_REQUIRED;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.URL_IS_REQUIRED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class MyBlog {
    @NotNull(message = URL_IS_REQUIRED)
    @Column(unique = true)
    URL blogUrl;

    @NotNull(message = MY_BLOG_VISIT_COUNT_IS_REQUIRED)
    Long myBlogVisitCount;

    String statusMessage;

    @Builder(access = AccessLevel.PROTECTED)
    public MyBlog(
            URL blogUrl,
            Long myBlogVisitCount,
            String statusMessage
    ) {
        Assert.notNull(blogUrl, URL_IS_REQUIRED);
        Assert.notNull(myBlogVisitCount, MY_BLOG_VISIT_COUNT_IS_REQUIRED);

        this.blogUrl = blogUrl;
        this.myBlogVisitCount = myBlogVisitCount;
        this.statusMessage = statusMessage;
    }
}
