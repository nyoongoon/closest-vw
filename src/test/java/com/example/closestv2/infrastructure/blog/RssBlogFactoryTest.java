package com.example.closestv2.infrastructure.blog;

import com.example.closestv2.clients.RssFeedClient;
import com.example.closestv2.domain.blog.BlogInfo;
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
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class RssBlogFactoryTest extends IntegrationTestSupport {
    @Autowired
    private RssBlogFactory rssBlogFactory;
    @Autowired
    private BlogRepository blogRepository;
    @MockBean
    private RssFeedClient rssFeedClient;

    private static Date toDate(LocalDateTime localDateTime) {
        // 서울 시간대 (UTC+9) 기준으로 Instant로 변환
        Instant instant = localDateTime.toInstant(ZoneOffset.ofHours(9));
        return Date.from(instant);
    }

    private static SyndFeed getMockSyndFeed(
            String link,
            String title,
            String author,
            LocalDateTime localDateTime
    ) {
        SyndFeed syndFeedMock = new SyndFeedImpl();
        syndFeedMock.setLink(link);
        syndFeedMock.setTitle(title);
        syndFeedMock.setAuthor(author);
        syndFeedMock.setPublishedDate(toDate(localDateTime));
        return syndFeedMock;
    }

    private static List<SyndEntry> getMockEntrys(
            String link,
            String title,
            LocalDateTime localDateTime
    ) {
        List<SyndEntry> mockEntrys = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            SyndEntryImpl entryMock = new SyndEntryImpl();
            entryMock.setLink(link + i);
            entryMock.setTitle(title + i);
            entryMock.setPublishedDate(toDate(localDateTime.plusMinutes(i)));
            mockEntrys.add(entryMock);
        }
        return mockEntrys;
    }

    @Test
    @DisplayName("URL로 RssClient를 통해서 Blog 객체를 얻을 수 있다.")
    void createBlog() throws MalformedURLException {
        //given
        URL url = new URL("https://goalinnext.tistory.com/rss");
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
        SyndFeed mockSyndFeed = getMockSyndFeed(
                "https://goalinnext.tistory.com/",
                "블로그 제목",
                "블로그 작가",
                localDateTime
        );
        Mockito.when(rssFeedClient.getSyndFeed(url)).thenReturn(mockSyndFeed); //mockking
        //when
        BlogRoot blog = rssBlogFactory.createRecentBlogRoot(url);
        //then
        BlogInfo blogInfo = blog.getBlogInfo();
        assertThat(blogInfo.blogUrl())
                .isEqualTo(new URL("https://goalinnext.tistory.com/"));
        assertThat(blogInfo.blogTitle())
                .isEqualTo("블로그 제목");
        assertThat(blogInfo.author())
                .isEqualTo("블로그 작가");
        assertThat(blogInfo.publishedDateTime())
                .isEqualTo(LocalDateTime.of(2022, 1, 1, 12, 3, 31));
    }

    @Test
    @DisplayName("SyndFeed에 SyndEntry가 존재하면 BlogRoot에 Post로 포함된다.")
    void createRecentBlogRoot() throws MalformedURLException {
        //given
        URL url = new URL("https://goalinnext.tistory.com/rss");
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
        SyndFeed mockSyndFeed = getMockSyndFeed(
                "https://goalinnext.tistory.com/",
                "블로그 제목",
                "블로그 작가",
                localDateTime
        );
        List<SyndEntry> mockEntrys = getMockEntrys(
                "https://goalinnext.tistory.com/",
                "포스트 제목",
                localDateTime
        );
        mockSyndFeed.setEntries(mockEntrys);
        Mockito.when(rssFeedClient.getSyndFeed(url)).thenReturn(mockSyndFeed); //mockking
        //when
        BlogRoot blog = rssBlogFactory.createRecentBlogRoot(url);
        //then
        List<Post> posts = blog.getPosts();
        assertThat(posts)
                .hasSize(3)
                .extracting(e -> e.getPostInfo().postUrl(), e -> e.getPostInfo().postTitle(), e -> e.getPostInfo().publishedDateTime())
                .containsExactly(
                        tuple(new URL("https://goalinnext.tistory.com/1"), "포스트 제목1", LocalDateTime.of(2022, 1, 1, 12, 4, 31)), //1분씩 증가
                        tuple(new URL("https://goalinnext.tistory.com/2"), "포스트 제목2", LocalDateTime.of(2022, 1, 1, 12, 5, 31)),
                        tuple(new URL("https://goalinnext.tistory.com/3"), "포스트 제목3", LocalDateTime.of(2022, 1, 1, 12, 6, 31))
                );
    }

}