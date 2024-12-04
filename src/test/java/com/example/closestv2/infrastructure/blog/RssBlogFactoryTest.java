package com.example.closestv2.infrastructure.blog;

import com.example.closestv2.domain.blog.BlogInfo;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.infrastructure.rss.RssBlogFactory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class RssBlogFactoryTest {
    private final URL ANY_RSS_URL = URI.create("https://example.com/rss").toURL();
    private final String ANY_LINK = "https://example.com";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    private final String ANY_TITLE = "제목";
    private final String ANY_AUTHOR = "작가";
    private RssBlogFactory sut;

    RssBlogFactoryTest() throws MalformedURLException {
    }

    private Date toDate(LocalDateTime localDateTime) {
        // 서울 시간대 (UTC+9) 기준으로 Instant로 변환
        Instant instant = localDateTime.toInstant(ZoneOffset.ofHours(9));
        return Date.from(instant);
    }

    private SyndFeed getSyndFeed(
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

    private void setEntries(
            SyndFeed syndFeed,
            String link,
            String title,
            LocalDateTime publishedDateTime
    ) {
        List<SyndEntry> entries = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            SyndEntryImpl entryMock = new SyndEntryImpl();
            entryMock.setLink(link + "/" + i);
            entryMock.setTitle(title + i);
            entryMock.setPublishedDate(toDate(publishedDateTime.plusMinutes(i)));
            entries.add(entryMock);
        }
        syndFeed.setEntries(entries);
    }

    @Test
    @DisplayName("URL로 RssClient를 통해서 Blog 객체를 얻을 수 있다.")
    void createBlog() throws MalformedURLException {
        //given
        SyndFeed syndFeed = getSyndFeed(ANY_LINK, ANY_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        sut = new RssBlogFactory();
        //when
        BlogRoot blog = sut.createRecentBlogRoot(ANY_RSS_URL, syndFeed);
        BlogInfo blogInfo = blog.getBlogInfo();
        //then
        assertThat(blogInfo.getBlogUrl()).isEqualTo(new URL(ANY_LINK));
        assertThat(blogInfo.getBlogTitle()).isEqualTo(ANY_TITLE);
        assertThat(blogInfo.getAuthor()).isEqualTo(ANY_AUTHOR);
    }

    @Test
    @DisplayName("URL로 RssClient를 통해서 Blog 객체를 얻을 때 Post로 변환되는 SyndEntry가 없다면 BlogRoot의 publishedDateTime은 LocalDateTime.MIN의 값이다.")
    void createBlogByLocalDateTimeMIN() throws MalformedURLException {
        //given
        SyndFeed syndFeed = getSyndFeed(ANY_LINK, ANY_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        sut = new RssBlogFactory();
        //when
        BlogRoot blog = sut.createRecentBlogRoot(ANY_RSS_URL, syndFeed);
        BlogInfo blogInfo = blog.getBlogInfo();
        //then
        assertThat(blogInfo.getPublishedDateTime()).isEqualTo(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Asia/Seoul")));
    }

    @Test
    @DisplayName("SyndFeed에 SyndEntry가 존재하면 BlogRoot에 Post로 포함된다.")
    void createRecentBlogRoot() throws MalformedURLException {
        //given
        SyndFeed syndFeed = getSyndFeed(ANY_LINK, ANY_TITLE, ANY_AUTHOR, ANY_PUBLISHED_DATE_TIME);
        setEntries(syndFeed, ANY_LINK, ANY_TITLE, ANY_PUBLISHED_DATE_TIME);
        sut = new RssBlogFactory();
        //when
        BlogRoot blog = sut.createRecentBlogRoot(ANY_RSS_URL, syndFeed);
        //then
        Map<URL, Post> posts = blog.getPosts();
        assertThat(posts.entrySet())
                .hasSize(3)
                .extracting(e -> e.getValue().getPostUrl(), e -> e.getValue().getPostTitle(), e -> e.getValue().getPublishedDateTime())
                .containsExactlyInAnyOrder(
                        tuple(URI.create(ANY_LINK + "/1").toURL(), ANY_TITLE + "1", ANY_PUBLISHED_DATE_TIME.plusMinutes(1)), //1분씩 증가
                        tuple(URI.create(ANY_LINK + "/2").toURL(), ANY_TITLE + "2", ANY_PUBLISHED_DATE_TIME.plusMinutes(2)),
                        tuple(URI.create(ANY_LINK + "/3").toURL(), ANY_TITLE + "3", ANY_PUBLISHED_DATE_TIME.plusMinutes(3))
                );
    }
}