package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class BlogSchedulerServiceTest extends IntegrationTestSupport {
    private final String ANY_LINK = "https://example.com";
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final String ANY_AUTHOR = "블로그 작가";
    private final String ANY_POST_TITLE = "블로그 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 10, 31); //기존 시간 10분
    @Autowired
    private BlogSchedulerService blogSchedulerService;
    @Autowired
    private BlogRepository blogRepository;
    @MockBean
    private BlogFactory blogFactory;


    @Test
    @DisplayName("업데이트 된 블로그를 확인 후 블로그 정보를 업데이트한다.")
    void pollingUpdatedBlogs() throws MalformedURLException, URISyntaxException {
        //given
        BlogRoot blogRoot = createBlog(URI.create(ANY_LINK).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        blogRepository.save(blogRoot);

        BlogRoot updatedBlog = createBlog(URI.create(ANY_LINK).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusMinutes(3));
        for (int i = 1; i < 4; i++) {
            savePost(
                    updatedBlog,
                    URI.create(ANY_LINK + "/" + i).toURL(),
                    ANY_POST_TITLE + i,
                    ANY_PUBLISHED_DATE_TIME.plusMinutes(i) //11, 12, 13분
            );
        }
        Mockito.when(blogFactory.createRecentBlogRoot(URI.create(ANY_LINK).toURL())).thenReturn(updatedBlog); //mockking

        //when
        blogSchedulerService.pollingUpdatedBlogs();
        //then
        //발행시간 업데이트
        blogRoot.getBlogInfo().getPublishedDateTime().isEqual(ANY_PUBLISHED_DATE_TIME.plusMinutes(3));
        List<Post> posts = blogRoot.getPosts();
        assertThat(posts)
                .hasSize(3)
                .extracting(Post::getPostUrl, Post::getPostTitle, Post::getPublishedDateTime)
                .containsExactly(
                        tuple(URI.create(ANY_LINK + "/" + 1).toURL(), ANY_POST_TITLE + "1", ANY_PUBLISHED_DATE_TIME.plusMinutes(1)),
                        tuple(URI.create(ANY_LINK + "/" + 2).toURL(), ANY_POST_TITLE + "2", ANY_PUBLISHED_DATE_TIME.plusMinutes(2)),
                        tuple(URI.create(ANY_LINK + "/" + 3).toURL(), ANY_POST_TITLE + "3", ANY_PUBLISHED_DATE_TIME.plusMinutes(3))
                );
    }

    @Test
    @DisplayName("업데이트 된 블로그를 확인 후 블로그 정보를 대량으로 업데이트한다.")
    void pollingUpdatedBlogsWithBulkUpdate() throws MalformedURLException, URISyntaxException {
        //given
        for (int i = 0; i < 100_000; i++) {
            BlogRoot blogRoot = createBlog(URI.create(ANY_LINK + "/" + i).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
            blogRepository.save(blogRoot);

            BlogRoot updatedBlog = createBlog(URI.create(ANY_LINK + i).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusMinutes(3));
            String link = blogRoot.getBlogInfo().getBlogUrl().toString();
            for (int j = 1; j < 1_000; j++) {
                savePost(
                        updatedBlog,
                        URI.create(link + "/" + i).toURL(),
                        ANY_POST_TITLE + i,
                        ANY_PUBLISHED_DATE_TIME.plusMinutes(i) //11, 12, 13분
                );
            }
            Mockito.when(blogFactory.createRecentBlogRoot(URI.create(ANY_LINK + i).toURL())).thenReturn(updatedBlog); //mockking
        }

        //when
        blogSchedulerService.pollingUpdatedBlogs();
        //then
    }

    private BlogRoot createBlog(
            URL blogUrl,
            String blogTitle,
            String author,
            LocalDateTime blogPusblishedDateTime
    ) {
        BlogRoot blogRoot = BlogRoot.create(
                blogUrl,
                blogTitle,
                author,
                blogPusblishedDateTime
        );
        return blogRoot;
    }

    private void savePost(
            BlogRoot blogRoot,
            URL postUrl,
            String postTitle,
            LocalDateTime postPusblishedDateTime
    ) {
        Post post = blogRoot.createPost(
                postUrl,
                postTitle,
                postPusblishedDateTime
        );
        blogRoot.getPosts().add(post);
    }
}