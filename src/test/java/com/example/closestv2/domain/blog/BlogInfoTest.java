package com.example.closestv2.domain.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogInfoTest {
    private final URL ANY_RSS_URL = URI.create("https://example.com/rss").toURL();
    private final URL ANY_BLOG_URL = URI.create("https://example.com/").toURL();
    private final String ANY_BLOG_TITLE = "제목";
    private final String ANY_AUTHOR = "작가";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    private final String ANY_STATUS_MESSAGE = "ANY_STATUS_MESSAGE";

    private BlogInfo sut;
    private BlogInfo.BlogInfoBuilder builder;

    BlogInfoTest() throws MalformedURLException {
    }


    @BeforeEach
    void setUp() {
        builder = BlogInfo.builder()
                .rssUrl(ANY_RSS_URL)
                .blogUrl(ANY_BLOG_URL)
                .author(ANY_AUTHOR)
                .blogTitle(ANY_BLOG_TITLE)
                .blogVisitCount(0L)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .statusMessage(ANY_STATUS_MESSAGE);
    }

    @Test
    @DisplayName("BlogInfo 생성 예외 케이스 - 필수값 검증")
    void createBlogInfoFailTest() {
        assertThatThrownBy(() -> sut = builder.rssUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.blogUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.blogTitle(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.author(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.publishedDateTime(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("BlogInfo 생성 성공 케이스")
    void createBlogInfoSuccessTest() {
        sut = builder.build();
        assertThat(sut.getRssUrl()).isEqualTo(ANY_RSS_URL);
        assertThat(sut.getBlogUrl()).isEqualTo(ANY_BLOG_URL);
        assertThat(sut.getAuthor()).isEqualTo(ANY_AUTHOR);
        assertThat(sut.getBlogTitle()).isEqualTo(ANY_BLOG_TITLE);
        assertThat(sut.getBlogVisitCount()).isEqualTo(0L);
        assertThat(sut.getPublishedDateTime()).isEqualTo(ANY_PUBLISHED_DATE_TIME);
        assertThat(sut.getStatusMessage()).isEqualTo(ANY_STATUS_MESSAGE);
    }
}