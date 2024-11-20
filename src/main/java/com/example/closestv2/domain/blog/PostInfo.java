package com.example.closestv2.domain.blog;

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
public class PostInfo {
    @NotNull(message = POST_URL_IS_REQUIRED)
    private URL postUrl;

    @NotBlank(message = POST_TITLE_IS_REQUIRED)
    private String postTitle;

    @NotNull(message = POST_PUBLISHED_DATETIME_IS_REQUIRED)
    private LocalDateTime publishedDateTime;

    @NotNull(message = POST_VISIT_COUNT_IS_REQUIRED)
    private Long postVisitCount;

    @Builder(access = AccessLevel.PROTECTED)
    private PostInfo(URL postUrl, String postTitle, LocalDateTime publishedDateTime, Long postVisitCount) {
        Assert.notNull(postUrl, POST_URL_IS_REQUIRED);
        Assert.hasText(postTitle, POST_TITLE_IS_REQUIRED);
        Assert.notNull(publishedDateTime, POST_PUBLISHED_DATETIME_IS_REQUIRED);
        Assert.notNull(postVisitCount, POST_VISIT_COUNT_IS_REQUIRED);

        this.postUrl = postUrl;
        this.postTitle = postTitle;
        this.publishedDateTime = publishedDateTime;
        this.postVisitCount = postVisitCount;
    }
}
