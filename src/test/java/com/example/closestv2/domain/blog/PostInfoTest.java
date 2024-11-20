package com.example.closestv2.domain.blog;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class PostInfoTest extends RepositoryTestSupport {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("Post 생성 시 PostInfo의 blogUrl, blogTitle, publishedDateTime으로 생성된다.")
    void createPostByPostInfo() throws MalformedURLException {
        //given
        URL postUrl = new URL("https://example.com/blog123/post/2");
        String postTitle = "포스트 제목";
        LocalDateTime publishedDateTime = LocalDateTime.of(2030, 1, 1, 12, 3, 31);

        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //when
        Post post = blogRoot.createPost(postUrl, postTitle, publishedDateTime);
        blogRoot.getPosts().add(post);
        blogRepository.save(blogRoot);
        //then
        Post savedPost = blogRoot.getPosts().getFirst();
        PostInfo postInfo = savedPost.getPostInfo();
        assertThat(postInfo)
                .extracting(PostInfo::getPostUrl, PostInfo::getPostTitle, PostInfo::getPublishedDateTime)
                .containsExactly(new URL("https://example.com/blog123/post/2"), "포스트 제목", LocalDateTime.of(2030, 1, 1, 12, 3, 31));
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 postUrl이 null이면 에러를 반환한다.")
    void createPostByPostInfoWithNullPostUrl() throws MalformedURLException {
        //given
        URL postUrl = null;

        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        Post post = blogRoot.createPost(postUrl, "포스트 제목", LocalDateTime.of(2030, 1, 1, 12, 3, 31));
        blogRoot.getPosts().add(post);
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 postTitle이 null이면 에러를 반환한다.")
    void createPostByPostInfoWithNullPostTitle() throws MalformedURLException {
        //given
        String postTitle = null;

        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        Post post = blogRoot.createPost(new URL("https://example.com/blog12/post/2"), postTitle, LocalDateTime.of(2030, 1, 1, 12, 3, 31));
        blogRoot.getPosts().add(post);
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 postTitle이 null이면 에러를 반환한다.")
    void createPostByPostInfoWithNullPushblishedDateTime() throws MalformedURLException {
        //given
        LocalDateTime publishedDateTime = null;

        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        Post post = blogRoot.createPost(new URL("https://example.com/blog12/post/2"), "포스트 제목", publishedDateTime);
        blogRoot.getPosts().add(post);
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }
}