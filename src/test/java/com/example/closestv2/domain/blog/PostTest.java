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

class PostTest {

    private final URL ANY_POST_URL = URI.create("https://example.com/blog123/post/2").toURL();
    private final String ANY_POST_TITLE = "포스트 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2030, 1, 1, 12, 3, 31);

    //todo postInfo -> post로 변경 중 ... url로 동등성 비교까지 해보기...
    private Post sut;
    private Post.PostBuilder builder;

    PostTest() throws MalformedURLException {
    }

    @BeforeEach
    public void setUp(){
        builder = Post.builder()
                .postUrl(ANY_POST_URL)
                .postTitle(ANY_POST_TITLE)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .postVisitCount(0L);
    }

    @Test
    @DisplayName("Post 생성 예외 케이스 - 필수값 검증")
    void createPostInfoFailTest(){
        assertThatThrownBy(() -> sut = builder.postUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle("").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle(" ").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.publishedDateTime(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Post 생성 성공 케이스")
    void createPostInfoSuccessTest(){
        sut = builder.build();
        assertThat(sut.getPostUrl()).isEqualTo(ANY_POST_URL);
        assertThat(sut.getPostTitle()).isEqualTo(ANY_POST_TITLE);
        assertThat(sut.getPublishedDateTime()).isEqualTo(ANY_PUBLISHED_DATE_TIME);
        assertThat(sut.getPostVisitCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Post isUpdated 비교 - 포스트 제목과 포스트 발행시간이 달라졌으면 true를 반환한다.")
    void isUpdatedTest(){
        Post sut = builder.build();
        assertThat(sut.isUpdated(builder.postTitle("변경제목").build())).isTrue();
        assertThat(sut.isUpdated(builder.publishedDateTime(ANY_PUBLISHED_DATE_TIME.plusSeconds(1)).build())).isTrue();
    }
}