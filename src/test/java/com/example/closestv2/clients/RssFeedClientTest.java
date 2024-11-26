package com.example.closestv2.clients;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class RssFeedClientTest  {
    private ClientAndServer mockServer;

    private final String RSS_FEED_RESPONSE = FileUtil.readFileAsString("rssResponse.xml");
    private static final String POST_ONE_TITLE = "Post One";
    private static final String POST_ONE_LINK = "http://localhost:8888/post-one";
    private static final String POST_TWO_TITLE = "Post Two";
    private static final String POST_TWO_LINK = "http://localhost:8888/post-two";

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

    @DisplayName("피드 읽어오기 테스트")
    @Test
    void getSyndFeed() throws MalformedURLException {
        RssFeedClient rssFeedClient = new RssFeedClient();
        SyndFeed syndFeed = rssFeedClient.getSyndFeed(URI.create("http://localhost:8888/rss").toURL());
        List<SyndEntry> entries = syndFeed.getEntries();

        assertAll(
                () -> assertThat(syndFeed.getLink()).isEqualTo("http://localhost:8888"),

                () -> assertThat(entries.get(0).getTitle()).isEqualTo(POST_ONE_TITLE),
                () -> assertThat(entries.get(0).getLink()).isEqualTo(POST_ONE_LINK),

                () -> assertThat(entries.get(1).getTitle()).isEqualTo(POST_TWO_TITLE),
                () -> assertThat(entries.get(1).getLink()).isEqualTo(POST_TWO_LINK)
        );
    }
}