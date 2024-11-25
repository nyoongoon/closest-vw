package com.example.closestv2.api.service;

import com.example.closestv2.clients.RssFeedClient;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.support.IntegrationTestSupport;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
    private RssFeedClient rssFeedClient;


    @Test
    @DisplayName("업데이트 된 블로그를 확인 후 블로그 정보를 업데이트한다.")
    void pollingUpdatedBlogs() throws MalformedURLException {
        //given
        BlogRoot blogRoot = createBlog(URI.create(ANY_LINK).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        blogRepository.save(blogRoot);
        setMock();
        //when
        blogSchedulerService.pollingUpdatedBlogs();
        //then
        //발행시간 업데이트
        BlogRoot found = blogRepository.findById(blogRoot.getId()).orElseThrow();
        found.getBlogInfo().getPublishedDateTime().isEqual(ANY_PUBLISHED_DATE_TIME.plusMinutes(3));
        List<Post> posts = found.getPosts();
        assertThat(posts)
                .hasSize(3)
                .extracting(Post::getPostUrl, Post::getPostTitle, Post::getPublishedDateTime)
                .containsExactly(
                        tuple(URI.create(ANY_LINK + "/" + 1).toURL(), ANY_POST_TITLE + "1", ANY_PUBLISHED_DATE_TIME.plusMinutes(1)),
                        tuple(URI.create(ANY_LINK + "/" + 2).toURL(), ANY_POST_TITLE + "2", ANY_PUBLISHED_DATE_TIME.plusMinutes(2)),
                        tuple(URI.create(ANY_LINK + "/" + 3).toURL(), ANY_POST_TITLE + "3", ANY_PUBLISHED_DATE_TIME.plusMinutes(3))
                );
    }

    private void setMock() throws MalformedURLException {
        SyndFeed mockSyndFeed = getMockSyndFeed(
                ANY_LINK,
                ANY_BLOG_TITLE,
                ANY_AUTHOR,
                ANY_PUBLISHED_DATE_TIME.plusMinutes(3)
        );

        setMockEntries(
                mockSyndFeed,
                ANY_LINK,
                ANY_POST_TITLE,
                ANY_PUBLISHED_DATE_TIME
        );

        Mockito.when(rssFeedClient.getSyndFeed(URI.create(ANY_LINK).toURL())).thenReturn(mockSyndFeed); //mockking
    }

//    @Test
//    @DisplayName("업데이트 된 블로그를 확인 후 블로그 정보를 대량으로 업데이트한다.")
//    void pollingUpdatedBlogsWithBulkUpdate() throws MalformedURLException, URISyntaxException {
//        //given
//        for (int i = 0; i < 100_000; i++) {
//            BlogRoot blogRoot = createBlog(URI.create(ANY_LINK + "/" + i).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
//            blogRepository.save(blogRoot);
//
//            BlogRoot updatedBlog = createBlog(URI.create(ANY_LINK + i).toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME.plusMinutes(3));
//            String link = blogRoot.getBlogInfo().getBlogUrl().toString();
//            for (int j = 1; j < 1_000; j++) {
//                savePost(
//                        updatedBlog,
//                        URI.create(link + "/" + i).toURL(),
//                        ANY_POST_TITLE + i,
//                        ANY_PUBLISHED_DATE_TIME.plusMinutes(i) //11, 12, 13분
//                );
//            }
//            Mockito.when(blogFactory.createRecentBlogRoot(URI.create(ANY_LINK + i).toURL())).thenReturn(updatedBlog); //mockking
//        }
//
//        //when
//        blogSchedulerService.pollingUpdatedBlogs();
//        //then
//    }

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

    private Date toDate(LocalDateTime localDateTime) {
        // 서울 시간대 (UTC+9) 기준으로 Instant로 변환
        Instant instant = localDateTime.toInstant(ZoneOffset.ofHours(9));
        return Date.from(instant);
    }

    private SyndFeed getMockSyndFeed(
            String link,
            String title,
            String author,
            LocalDateTime publishedDateTime
    ) {
        SyndFeed syndFeedMock = new SyndFeedImpl();
        syndFeedMock.setLink(link);
        syndFeedMock.setTitle(title);
        syndFeedMock.setAuthor(author);
        syndFeedMock.setPublishedDate(toDate(publishedDateTime));
        return syndFeedMock;
    }

    private void setMockEntries(
            SyndFeed syndFeed,
            String link,
            String title,
            LocalDateTime publishedDateTime
    ) {
        List<SyndEntry> mockEntries = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            SyndEntryImpl entryMock = new SyndEntryImpl();
            entryMock.setLink(link + "/" + i);
            entryMock.setTitle(title + i);
            entryMock.setPublishedDate(toDate(publishedDateTime.plusMinutes(i)));
            mockEntries.add(entryMock);
        }
        syndFeed.setEntries(mockEntries);
    }
}