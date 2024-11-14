package com.example.closestv2.domain.blog;

import com.example.closestv2.support.RepositoryTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class BlogRootTest extends RepositoryTestSupport {
    @Autowired
    private BlogRepository blogRepository;

    @Test
    @DisplayName("Blog는 BlogInfo의 상태메시지를 변경할 수 있다.")
    void withStatusMessage() throws MalformedURLException {
        //given
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        blogRepository.save(blogRoot);
        String statusMessage = "변경할 상태 메시지";
        //when
        blogRoot.withStatusMessage(statusMessage);
        //then
        assertThat(blogRoot.getBlogInfo().statusMessage())
                .isEqualTo("변경할 상태 메시지");
    }

    @Test
    @DisplayName("블로그가 자신이 참조하고 있는 포스트를 postUrl을 기준으로 제거할 수 있다.")
    void blogDeletePost() throws MalformedURLException {
        //given
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        Post post = blogRoot.createPost(new URL("https://example.com/blog123/post/2"), "포스트 제목", LocalDateTime.of(2030, 1, 1, 12, 3, 31));
        blogRoot.getPosts().add(post);
        blogRepository.save(blogRoot);

        //when
        URL findUrl = new URL("https://example.com/blog123/post/2");
        Post foundPost = blogRoot.getPosts().stream()
                .filter(e -> e.getPostInfo().postUrl().equals(findUrl))
                .findFirst()
                .orElseThrow();
        blogRoot.getPosts().remove(foundPost);

        //then
        assertThatThrownBy(() ->
                blogRoot.getPosts().stream()
                        .filter(e -> e.getPostInfo().postUrl().equals(findUrl))
                        .findFirst()
                        .orElseThrow()
                )
                .isInstanceOf(NoSuchElementException.class);
    }


    @Test
    @DisplayName("Blog 생성 시 BlogInfo의 blogVisitCount는 0이 된다.")
    void createBlogByBlogInfoWithZeroBlogVisitCount() throws MalformedURLException {
        //given
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        //when
        blogRepository.save(blogRoot);
        //then
        assertThat(blogRoot.getBlogInfo().blogVisitCount())
                .isEqualTo(0L);
    }

    @Test
    @DisplayName("Post 생성 시 PostInfo의 postVisitCount는 0이 된다.")
    void createPostByPostInfoWithZeroPostVisitCount() throws MalformedURLException {
        //given
        BlogRoot blogRoot = BlogRoot.create(
                new URL("https://example.com/blog123"),
                "제목",
                "작가",
                LocalDateTime.of(2022, 1, 1, 12, 3, 31)
        );
        Post post = blogRoot.createPost(new URL("https://example.com/blog123/post/2"), "포스트 제목", LocalDateTime.of(2030, 1, 1, 12, 3, 31));
        blogRoot.getPosts().add(post);
        //when
        blogRepository.save(blogRoot);
        //then
        assertThat(post.getPostInfo().postVisitCount())
                .isEqualTo(0L);
    }
}