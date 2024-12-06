package com.example.closestv2.domain.likes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LikesRootTest {

    private final URL ANY_URL = URI.create("https://example.com/123").toURL();
    private LikesRoot sut;

    LikesRootTest() throws MalformedURLException {
    }

    @DisplayName("LikesRoot 생성 예외 케이스 - 필수값 검증")
    @Test
    void createLikesFailTest() {
        assertThatThrownBy(() -> LikesRoot.create(1L, null));
    }

    @DisplayName("LikesRoot 생성 성공 케이스")
    @Test
    void createLikesSuccessTest() {
        sut = LikesRoot.create(1L, ANY_URL);
    }
}