package com.example.closestv2.domain.blog;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull(message = POST_URL_IS_REQUIRED)
    @Column(name = "post_url")
    private URL postUrl;

    @NotBlank(message = POST_TITLE_IS_REQUIRED)
    private String postTitle;

    @NotNull(message = POST_PUBLISHED_DATETIME_IS_REQUIRED)
    private LocalDateTime publishedDateTime;

    @NotNull(message = POST_VISIT_COUNT_IS_REQUIRED)
    private Long postVisitCount;

    @Builder(access = AccessLevel.PROTECTED)
    private Post(
            URL postUrl,
            String postTitle,
            LocalDateTime publishedDateTime,
            Long postVisitCount
    ) {
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
