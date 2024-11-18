package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@Transactional
class BlogSchedulerServiceTest extends IntegrationTestSupport {
    @Autowired
    private BlogSchedulerService blogSchedulerService;
    @Autowired
    private BlogRepository blogRepository;
    @MockBean
    private BlogFactory blogFactory;


    @Test
    @DisplayName("업데이트 된 블로그를 확인 후 블로그 정보를 업데이트한다.")
    void pollingUpdatedBlogs() throws MalformedURLException {
        //given
        BlogRoot blogRoot = saveBlog(
                new URL("https://example.com/"),
                "블로그 제목",
                "블로그 작가",
                LocalDateTime.of(2022, 1, 1, 12, 13, 31) //마지막 포스트 업데이트 시간 13분
        );
        for (int i = 1; i < 4; i++) {
            savePost(
                    blogRoot,
                    new URL("https://example.com/" + i),
                    "포스트 제목" + i,
                    LocalDateTime.of(2022, 1, 1, 12, 10, 31).plusMinutes(i) //11, 12, 13분
            );
        }


        //when
        //then
    }

    private BlogRoot saveBlog(
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
        blogRepository.save(blogRoot);
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