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
class BlogInfoTest extends RepositoryTestSupport {
    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("Blog 생성 시 postUrl, blogTitle, author, publishedDateTime으로 BlogInfo을 생성한다.")
    void createBlogByBlogInfo() throws MalformedURLException {
        //given
        URL blogUrl = new URL("https://example.com/blog123");
        String blogTitle = "제목";
        String author = "작가";
        LocalDateTime publishedDateTime = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
        BlogRoot blogRoot = BlogRoot.create(
                blogUrl,
                blogTitle,
                author,
                publishedDateTime
        );
        // when
        blogRepository.save(blogRoot);
        // then
        BlogInfo blogInfo = blogRoot.getBlogInfo();
        assertThat(blogInfo)
                .extracting(BlogInfo::blogUrl, BlogInfo::blogTitle, BlogInfo::author, BlogInfo::publishedDateTime)
                .containsExactly(new URL("https://example.com/blog123"), "제목", "작가", LocalDateTime.of(2022, 1, 1, 12, 3, 31));
    }

    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 url이 null이면 에러가 발생한다.")
    void createBlogInfoWithNullUrl() {
        //given
        URL blogUrl = null;
        //when
        BlogRoot blogRoot = BlogRoot.create(
                blogUrl,
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 title이 null이면 에러가 발생한다.")
    void createBlogInfoWithNullTitle() throws MalformedURLException {
        //given
        String blogTitle = null;
        //when
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                blogTitle,
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 author이 null이면 에러가 발생한다.")
    void createBlogInfoWithNullAuthor() throws MalformedURLException {
        //given
        String author = null;
        //when
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                author,
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 url이 null이면 에러가 발생한다.")
    void createBlogInfoWithNullPublishedDateTime() throws MalformedURLException {
        //given
        LocalDateTime publishedDateTime = null;
        //when
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                publishedDateTime
        );
        //expected
        assertThatThrownBy(() -> blogRepository.save(blogRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("BlogInfo는 값객체로서 동등성 비교를 할 수 있다.")
    void hasBlogInfoEquality() throws MalformedURLException {
        //given
        BlogRoot blogRoot1 = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        BlogRoot blogRoot2 = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //expected
        assertThat(blogRoot1.getBlogInfo() == blogRoot2.getBlogInfo())
                .isFalse(); // 동일성(identity)
        assertThat(blogRoot1.getBlogInfo().equals(blogRoot2.getBlogInfo()))
                .isTrue(); // 동등성(equality)
    }
}