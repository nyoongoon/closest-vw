package com.example.closestv2.domain.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class BlogRootTest {
    //Blog
    private final URL ANY_BLOG_URL = URI.create("https://example.com/blog123").toURL();
    private final String ANY_BLOG_TITLE = "제목";
    private final String ANY_AUTHOR = "작가";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    //Post
    private final URL ANY_POST_URL = URI.create("https://example.com/blog123/123").toURL();
    private final String ANY_POST_TITLE = "포스트 제목";

    BlogRootTest() throws MalformedURLException {
    }


    @Test
    @DisplayName("Blog는 BlogInfo의 상태메시지를 변경할 수 있다.")
    void editStatusMessage() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        String statusMessage = "변경할 상태 메시지";
        //when
        sut.editStatusMessage(statusMessage);
        //then
        assertThat(sut.getBlogInfo().getStatusMessage()).isEqualTo("변경할 상태 메시지");
    }

    @Test
    @DisplayName("블로그가 자신이 참조하고 있는 포스트를 postUrl을 기준으로 제거할 수 있다.")
    void blogDeletePost() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        Post post = sut.createPost(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
        sut.getPosts().add(post);

        URL findUrl = ANY_POST_URL;
        Post foundPost = sut.getPosts().stream()
                .filter(e -> e.getPostUrl().equals(findUrl))
                .findFirst()
                .orElseThrow();

        //when
        sut.getPosts().remove(foundPost);

        //then
        assertThatThrownBy(() ->
                sut.getPosts().stream()
                        .filter(e -> e.getPostUrl().equals(findUrl))
                        .findFirst()
                        .orElseThrow()
        ).isInstanceOf(NoSuchElementException.class);
    }


    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 blogVisitCount는 0이 된다.")
    void createBlogByBlogInfoWithZeroBlogVisitCount() throws MalformedURLException {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //expected
        assertThat(sut.getBlogInfo().getBlogVisitCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 postVisitCount는 0이 된다.")
    void createPostByPostInfoWithZeroPostVisitCount() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        Post post = sut.createPost(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
        //expected
        assertThat(post.getPostVisitCount()).isEqualTo(0L);
    }


    @Test
    @DisplayName("블로그 업데이트 확인 여부 시 BlogInfo의 blogTitle, author, publicshedDateTime을 비교한다. - true 케이스")
    void isUpdatedByEqualBlogInfoTrueTest() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot updatedRecentBlogRoot1 = BlogRoot.create(ANY_BLOG_URL, "업데이트 제목", ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot updatedRecentBlogRoot2 = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, "업데이트 작가", ANY_PUBLISHED_DATE_TIME);
        BlogRoot updatedRecentBlogRoot3 = BlogRoot.create(ANY_BLOG_URL, "업데이트 제목", ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusSeconds(1)); //1초 증가
        //when
        boolean isUpdated1 = sut.isBlogUpdated(updatedRecentBlogRoot1);
        boolean isUpdated2 = sut.isBlogUpdated(updatedRecentBlogRoot2);
        boolean isUpdated3 = sut.isBlogUpdated(updatedRecentBlogRoot3);
        //then
        assertThat(isUpdated1).isTrue();
        assertThat(isUpdated2).isTrue();
        assertThat(isUpdated3).isTrue();
    }

    @Test
    @DisplayName("블로그 업데이트 확인 여부 시 BlogInfo의 blogTitle, author, publicshedDateTime을 비교한다. - false 케이스")
    void isUpdatedByEqualBlogInfoFalseTest() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot updatedRecentBlogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //when
        boolean isUpdated = sut.isBlogUpdated(updatedRecentBlogRoot);
        //then
        assertThat(isUpdated).isFalse();
    }

    @Test
    @DisplayName("블로그 업데이트 시 BlogInfo의 값들을 비교할 때 URL이 일치하지 않으면 에러를 반환한다.")
    void updateBlogRootByUnEqualUrlThrowError() throws MalformedURLException {
        //given
        BlogRoot blogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot notSameUrlBlog = BlogRoot.create(URI.create("https://example.com/blog123X").toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //expected
        assertThatThrownBy(() -> blogRoot.updateBlogRoot(notSameUrlBlog)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("블로그 업데이트 시 발생시간 비교 시 기존 블로그 발행시간보다 조회한 발행시간이 과거일 경우 에러가 발생한다.")
    void updateBlogRootWithPostPublishedDateTime() {
        //given
        BlogRoot blogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot pastBlogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.minusSeconds(1));
        //expected
        assertThatThrownBy(() -> blogRoot.updateBlogRoot(pastBlogRoot)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("블로그 업데이트 시 blogTitle, author, publishedDateTime이 다르다면 해당 값들이 변경된다.")
    void updateBlogRoot() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        BlogRoot compared = BlogRoot.create(ANY_BLOG_URL, "변경 제목", "변경 작가", ANY_PUBLISHED_DATE_TIME.plusSeconds(1));
        //when
        sut.updateBlogRoot(compared);
        //then
        assertThat(sut.getBlogInfo().getBlogTitle()).isEqualTo("변경 제목");
        assertThat(sut.getBlogInfo().getAuthor()).isEqualTo("변경 작가");
        assertThat(sut.getBlogInfo().getPublishedDateTime()).isEqualTo(ANY_PUBLISHED_DATE_TIME.plusSeconds(1));
    }

    @Test
    @DisplayName("블로그 업데이트 시 BlogInfo의 PublishedDate가 변경되었다면 포스트 정보도 변경된 것이다.")
    void isUpdatedPostsByPublishedDate() {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //when
        BlogRoot compared = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusSeconds(1));
        //then
        assertThat(sut.isBlogUpdated(compared)).isTrue();
    }

    @Test
    @DisplayName("포스트 업데이트 시 전달된 BlogRoot의 post를 기존 posts에 추가한다.")
    void updatePosts() throws MalformedURLException {
        //given
        BlogRoot sut = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        List<Post> sutPosts = sut.getPosts();
        sutPosts.add(sut.createPost(URI.create("https://example.com/blog123/1").toURL(), "포스트 제목1", ANY_PUBLISHED_DATE_TIME.plusSeconds(1)));
        BlogRoot compared = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusSeconds(4));
        List<Post> comparedPosts = compared.getPosts();
        comparedPosts.add(compared.createPost(URI.create("https://example.com/blog123/2").toURL(), "포스트 제목2", ANY_PUBLISHED_DATE_TIME.plusSeconds(2)));
        comparedPosts.add(compared.createPost(URI.create("https://example.com/blog123/3").toURL(), "포스트 제목3", ANY_PUBLISHED_DATE_TIME.plusSeconds(3)));
        comparedPosts.add(compared.createPost(URI.create("https://example.com/blog123/4").toURL(), "포스트 제목4", ANY_PUBLISHED_DATE_TIME.plusSeconds(4)));
        //when
        sut.updateBlogRoot(compared);
        //then
        assertThat(sut.getPosts())
                .hasSize(4)
                .extracting(Post::getPostUrl, Post::getPostTitle, Post::getPublishedDateTime)
                .containsExactly(
                        tuple(URI.create("https://example.com/blog123/1").toURL(), "포스트 제목1", ANY_PUBLISHED_DATE_TIME.plusSeconds(1)),
                        tuple(URI.create("https://example.com/blog123/2").toURL(), "포스트 제목2", ANY_PUBLISHED_DATE_TIME.plusSeconds(2)),
                        tuple(URI.create("https://example.com/blog123/3").toURL(), "포스트 제목3", ANY_PUBLISHED_DATE_TIME.plusSeconds(3)),
                        tuple(URI.create("https://example.com/blog123/4").toURL(), "포스트 제목4", ANY_PUBLISHED_DATE_TIME.plusSeconds(4))
                );
    }


    @Test
    @DisplayName("Post 생성 시 Blog의 posts 리스트에 추가되어 생성된다.")
    void createPost() {
        //given
        BlogRoot blogRoot = BlogRoot.create(ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        //when
        Post post = blogRoot.createPost(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
        blogRoot.getPosts().add(post);
        //then
        Post savedPost = blogRoot.getPosts().getFirst();

        assertThat(savedPost)
                .extracting(Post::getPostUrl, Post::getPostTitle, Post::getPublishedDateTime)
                .containsExactly(ANY_POST_URL, ANY_POST_TITLE, ANY_PUBLISHED_DATE_TIME);
    }
}