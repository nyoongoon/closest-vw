package com.example.closestv2.infrastructure.blog;

import com.example.closestv2.clients.RssFeedClient;
import com.example.closestv2.domain.blog.BlogInfo;
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

class RssBlogFactoryTest extends IntegrationTestSupport {
    private final String ANY_LINK = "https://example.com/blog123";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    private final String ANY_TITLE = "제목";
    private final String ANY_AUTHOR = "작가";
    @Autowired
    private RssBlogFactory rssBlogFactory;
    @MockBean
    private RssFeedClient rssFeedClient;

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

    private List<SyndEntry> getMockEntrys(
            String link,
            String title,
            LocalDateTime publishedDateTime
    ) {
        List<SyndEntry> mockEntrys = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            SyndEntryImpl entryMock = new SyndEntryImpl();
            entryMock.setLink(link + "/"+i);
            entryMock.setTitle(title + i);
            entryMock.setPublishedDate(toDate(publishedDateTime.plusMinutes(i)));
            mockEntrys.add(entryMock);
        }
        return mockEntrys;
    }

    @Test
    @DisplayName("URL로 RssClient를 통해서 Blog 객체를 얻을 수 있다.")
    void createBlog() throws MalformedURLException {
        //given
        URL url = URI.create(ANY_LINK).toURL();
        SyndFeed mockSyndFeed = getMockSyndFeed(ANY_LINK, ANY_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        Mockito.when(rssFeedClient.getSyndFeed(url)).thenReturn(mockSyndFeed); //mockking
        //when
        BlogRoot blog = rssBlogFactory.createRecentBlogRoot(url);
        BlogInfo blogInfo = blog.getBlogInfo();
        //then
        assertThat(blogInfo.getBlogUrl()).isEqualTo(new URL(ANY_LINK));
        assertThat(blogInfo.getBlogTitle()).isEqualTo(ANY_TITLE);
        assertThat(blogInfo.getAuthor()).isEqualTo(ANY_AUTHOR);
        assertThat(blogInfo.getPublishedDateTime()).isEqualTo(LocalDateTime.of(2022, 1, 1, 12, 3, 31));
    }

    @Test
    @DisplayName("SyndFeed에 SyndEntry가 존재하면 BlogRoot에 Post로 포함된다.")
    void createRecentBlogRoot() throws MalformedURLException {
        //given
        URL url = new URL(ANY_LINK);
        SyndFeed mockSyndFeed = getMockSyndFeed(ANY_LINK, ANY_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        List<SyndEntry> mockEntrys = getMockEntrys(ANY_LINK, ANY_TITLE, ANY_PUBLISHED_DATE_TIME);
        mockSyndFeed.setEntries(mockEntrys);
        Mockito.when(rssFeedClient.getSyndFeed(url)).thenReturn(mockSyndFeed); //mockking
        //when
        BlogRoot blog = rssBlogFactory.createRecentBlogRoot(url);
        //then
        List<Post> posts = blog.getPosts();
        assertThat(posts)
                .hasSize(3)
                .extracting(e -> e.getPostInfo().getPostUrl(), e -> e.getPostInfo().getPostTitle(), e -> e.getPostInfo().getPublishedDateTime())
                .containsExactly(
                        tuple(URI.create(ANY_LINK + "/1").toURL(), ANY_TITLE+"1", ANY_PUBLISHED_DATE_TIME.plusMinutes(1)), //1분씩 증가
                        tuple(URI.create(ANY_LINK + "/2").toURL(), ANY_TITLE+"2", ANY_PUBLISHED_DATE_TIME.plusMinutes(2)),
                        tuple(URI.create(ANY_LINK + "/3").toURL(), ANY_TITLE+"3", ANY_PUBLISHED_DATE_TIME.plusMinutes(3))
                );
    }

}