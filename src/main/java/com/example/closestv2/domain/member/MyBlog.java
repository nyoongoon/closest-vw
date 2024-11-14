package com.example.closestv2.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MY_BLOG_VISIT_COUNT_IS_REQUIRED;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.URL_IS_REQUIRED;

@Builder(access = AccessLevel.PROTECTED)
@Embeddable
record MyBlog(
        @NotNull(message = URL_IS_REQUIRED)
        @Column(unique = true)
        URL blogUrl,

        @NotNull(message = MY_BLOG_VISIT_COUNT_IS_REQUIRED)
        Long myBlogVisitCount,

        String statusMessage
) {
}
