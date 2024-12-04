package com.example.closestv2.clients;

import com.example.closestv2.domain.blog.BlogInfo;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.infrastructure.domain.feed.RssFeedClient;
import com.example.closestv2.infrastructure.rss.RssBlogFactory;
import com.example.closestv2.util.file.FileUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class RssFeedClientTest  {
    private ClientAndServer mockServer;

    private final String RSS_FEED_RESPONSE = FileUtil.readFileAsString("rssResponse.xml");
    private static final String POST_ONE_TITLE = "포스트 제목 1";
    private static final String POST_ONE_LINK = "http://localhost:8888/1";
    private static final String POST_TWO_TITLE = "포스트 제목 2";
    private static final String POST_TWO_LINK = "http://localhost:8888/2";

    RssFeedClientTest() throws IOException {
    }

    // ClientAndServer 객체를 이용해서 mockServer를 킵니다
    // MockServerClient 객체에서 목킹할 요청과 응답을 설정합니다
    @BeforeEach
    void setUp() {
        mockServer = ClientAndServer.startClientAndServer(8888);
        new MockServerClient("localhost", 8888)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/rss")
                )
                .respond(
                        response()
                                .withHeader(new Header("Content-Type", "text/xml;charset=utf-8"))
                                .withBody(RSS_FEED_RESPONSE)
                );
    }

    // ClientAndServer 객체를 이용해서 mockServer를 끕니다
    @AfterEach
    void shutDown() {
        mockServer.stop();
    }


    @Test
    @DisplayName("SyndFeed를 읽어와서 Feed 객체로 변환한다.")
    void getFeed(){
        //given
        //when
        //then
        throw new IllegalStateException();
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

    @DisplayName("피드 읽어오기 테스트")
    @Test
    void getSyndFeed() throws MalformedURLException {
        RssFeedClient rssFeedClient = new RssFeedClient();
        SyndFeed syndFeed = rssFeedClient.getSyndFeed(URI.create("http://localhost:8888/rss").toURL());
        List<SyndEntry> entries = syndFeed.getEntries();

        assertAll(
                () -> assertThat(syndFeed.getLink()).isEqualTo("http://localhost:8888/"),

                () -> assertThat(entries.get(0).getTitle()).isEqualTo(POST_ONE_TITLE),
                () -> assertThat(entries.get(0).getLink()).isEqualTo(POST_ONE_LINK),

                () -> assertThat(entries.get(1).getTitle()).isEqualTo(POST_TWO_TITLE),
                () -> assertThat(entries.get(1).getLink()).isEqualTo(POST_TWO_LINK)
        );
    }
}