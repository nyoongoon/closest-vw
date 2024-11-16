package com.example.closestv2.domain.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogInfoTest {

    private final static URL ANY_BLOG_URL;

    static {
        try {
            ANY_BLOG_URL = URI.create("https://example.com/blog123").toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private final static String ANY_BLOG_TITLE = "제목";
    private final static String ANY_AUTHOR = "작가";
    private final static LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    private final static String ANY_STATUS_MESSAGE = "ANY_STATUS_MESSAGE";

    BlogInfo sut;

    BlogInfo.BlogInfoBuilder builder;

    @BeforeEach
    void setUp() {
        builder = BlogInfo.builder()
                .blogUrl(ANY_BLOG_URL)
                .author(ANY_AUTHOR)
                .blogTitle(ANY_BLOG_TITLE)
                .blogVisitCount(0L)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .statusMessage(ANY_STATUS_MESSAGE);
    }

    @Test
    void createFailTest() {
        assertThatThrownBy(() -> sut = builder.blogUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.blogTitle(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.author(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.publishedDateTime(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createSuccessTest() {
        sut = builder.build();
        assertThat(sut).isEqualTo(BlogInfo.builder()
                .blogUrl(ANY_BLOG_URL)
                .author(ANY_AUTHOR)
                .blogTitle(ANY_BLOG_TITLE)
                .blogVisitCount(0L)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .statusMessage(ANY_STATUS_MESSAGE)
                .build()
        );
    }
}
