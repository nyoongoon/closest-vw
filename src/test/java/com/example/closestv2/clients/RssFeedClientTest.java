package com.example.closestv2.clients;

import com.example.closestv2.domain.blog.BlogInfo;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.domain.feed.Feed;
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
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class RssFeedClientTest  {
    private ClientAndServer mockServer;
    private RssFeedClient rssFeedClient;
    private final URL ANY_RSS_URL = URI.create("http://localhost:8888/rss").toURL();
    private final String RSS_FEED_RESPONSE = FileUtil.readFileAsString("rssResponse.xml");
    private  final String POST_ONE_TITLE = "포스트 제목 1";
    private  final String POST_ONE_LINK = "http://localhost:8888/1";
    private final LocalDateTime POST_ONE_PUBLISHED_DATE_TIME = LocalDateTime.of(2030, 1, 1, 12, 4, 31);
    private  final String POST_TWO_TITLE = "포스트 제목 2";
    private  final String POST_TWO_LINK = "http://localhost:8888/2";
    private final LocalDateTime POST_TWO_PUBLISHED_DATE_TIME = LocalDateTime.of(2030, 1, 1, 12, 5, 31);

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
        rssFeedClient = new RssFeedClient();
    }

    // ClientAndServer 객체를 이용해서 mockServer를 끕니다
    @AfterEach
    void shutDown() {
        mockServer.stop();
    }


    @Test
    @DisplayName("RssFeedClient에 URL를 요청하여 Feed 객체를 얻는다..")
    void getFeed() throws MalformedURLException {
        //given
        //when
        Feed feed = rssFeedClient.getFeed(ANY_RSS_URL);
        //then
        assertThat(feed.getRssUrl()).isEqualTo(ANY_RSS_URL);
    }


    @Test
    @DisplayName("URL로 RssClient를 통해서 Blog 객체를 얻을 때 Post로 변환되는 SyndEntry가 없다면 BlogRoot의 publishedDateTime은 LocalDateTime.MIN의 값이다.")
    void createBlogByLocalDateTimeMIN() throws MalformedURLException {
        //when
        Feed feed = rssFeedClient.getFeed(ANY_RSS_URL);
        //given
        BlogRoot blogRoot = feed.toBlogRoot();
        //then
        assertThat(blogRoot.getBlogInfo().getPublishedDateTime()).isEqualTo(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Asia/Seoul")));
    }


    @Test
    @DisplayName("SyndFeed에 SyndEntry가 존재하면 BlogRoot에 Post로 포함된다.")
    void createRecentBlogRoot() throws MalformedURLException {
        //given
        Feed feed = rssFeedClient.getFeed(ANY_RSS_URL);
        //when
        BlogRoot blogRoot = feed.toBlogRoot();
        //then
        Map<URL, Post> posts = blogRoot.getPosts();
        assertThat(posts.entrySet())
                .hasSize(2)
                .extracting(e -> e.getValue().getPostUrl(), e -> e.getValue().getPostTitle(), e -> e.getValue().getPublishedDateTime())
                .containsExactlyInAnyOrder(
                        tuple(URI.create(POST_ONE_LINK).toURL(), POST_ONE_TITLE, POST_ONE_PUBLISHED_DATE_TIME),
                        tuple(URI.create(POST_TWO_LINK).toURL(), POST_TWO_TITLE, POST_TWO_PUBLISHED_DATE_TIME)
                );
    }
}