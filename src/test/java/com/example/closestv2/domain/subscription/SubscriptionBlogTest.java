package com.example.closestv2.domain.subscription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionBlogTest {
    private final URL ANY_BLOG_URL = URI.create("https://example.com/blog123").toURL();
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);

    private SubscriptionBlog sut;
    private SubscriptionBlog.SubscriptionBlogBuilder builder;

    SubscriptionBlogTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp() {
        builder = SubscriptionBlog.builder()
                .blogUrl(ANY_BLOG_URL)
                .blogTitle(ANY_BLOG_TITLE)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .newPostCount(0L);
    }

    @Test
    @DisplayName("BlogInfo 생성 예외 케이스 - 필수값 검증")
    void createSubscriptionBlogFailTest() {
        assertThatThrownBy(() -> sut = builder.blogUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.blogTitle(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.publishedDateTime(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.newPostCount(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("BlogInfo 생성 성공 케이스")
    void createSubscriptionBlogSuccessTest() {
        sut = builder.build();
        assertThat(sut.getBlogUrl()).isEqualTo(ANY_BLOG_URL);
        assertThat(sut.getBlogTitle()).isEqualTo(ANY_BLOG_TITLE);
        assertThat(sut.getPublishedDateTime()).isEqualTo(ANY_PUBLISHED_DATE_TIME);
        assertThat(sut.getNewPostCount()).isEqualTo(0L);
    }
}