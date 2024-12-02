package com.example.closestv2.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MyBlogTest {
    private final URL ANY_URL = URI.create("https://example.com/blog123").toURL();
    private MyBlog sut;
    private MyBlog.MyBlogBuilder builder;

    MyBlogTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp() {
        builder = MyBlog.builder()
                .blogUrl(ANY_URL)
                .myBlogVisitCount(0L);
    }

    @Test
    @DisplayName("MyBlog 생성 예외 케이스 - 필수값 검증")
    void createMyBlogFailTest() {
        assertThatThrownBy(() -> builder.blogUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("MyBlog 생성 성공 케이스")
    void createMyBlogSuccessTest() {
        sut = builder.build();
        assertThat(sut.getBlogUrl()).isEqualTo(ANY_URL);
        assertThat(sut.getMyBlogVisitCount()).isEqualTo(0L);
    }
}