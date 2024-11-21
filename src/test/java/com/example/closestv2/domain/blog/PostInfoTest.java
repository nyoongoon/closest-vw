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

class PostInfoTest {
    private final URL ANY_BLOG_URL = URI.create("https://example.com/blog123").toURL();
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final String ANY_AUTHOR = "작가";
    private final URL ANY_POST_URL = URI.create("https://example.com/blog123/post/2").toURL();
    private final String ANY_POST_TITLE = "포스트 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2030, 1, 1, 12, 3, 31);

    PostInfoTest() throws MalformedURLException {
    }

    private PostInfo sut;
    private PostInfo.PostInfoBuilder builder;

    @BeforeEach
    public void setUp(){
        builder = PostInfo.builder()
                .postUrl(ANY_POST_URL)
                .postTitle(ANY_POST_TITLE)
                .publishedDateTime(ANY_PUBLISHED_DATE_TIME)
                .postVisitCount(0L);
    }

    @Test
    @DisplayName("PostInfo 생성 예외 케이스 - 필수값 검증")
    void createPostInfoFailTest(){
        assertThatThrownBy(() -> sut = builder.postUrl(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle("").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postTitle(" ").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.publishedDateTime(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.postVisitCount(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("PostInfo 생성 성공 케이스")
    void createPostInfoSuccessTest(){
        sut = builder.build();
        assertThat(sut.getPostUrl()).isEqualTo(ANY_POST_URL);
        assertThat(sut.getPostTitle()).isEqualTo(ANY_POST_TITLE);
        assertThat(sut.getPublishedDateTime()).isEqualTo(ANY_PUBLISHED_DATE_TIME);
        assertThat(sut.getPostVisitCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 blogUrl, blogTitle, publishedDateTime으로 생성된다.")
    void createPostByPostInfo() {
        //given
        BlogRoot blogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //when
        Post post = blogRoot.createPost(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
        blogRoot.getPosts().add(post);
        //then
        Post savedPost = blogRoot.getPosts().getFirst();
        PostInfo postInfo = savedPost.getPostInfo();
        assertThat(postInfo)
                .extracting(PostInfo::getPostUrl, PostInfo::getPostTitle, PostInfo::getPublishedDateTime)
                .containsExactly(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
    }
}