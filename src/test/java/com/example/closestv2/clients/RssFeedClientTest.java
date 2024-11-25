package com.example.closestv2.clients;

import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class RssFeedClientTest  {
    @Test
    @DisplayName("Url로 SyndFeed 객체를 조회할 수 있다.")
    void getSyndFeed() throws MalformedURLException {
        //given
        RssFeedClient rssFeedClient = new RssFeedClient();
        URL url = new URL("https://goalinnext.tistory.com/rss");
        //when
        SyndFeed syndFeed = rssFeedClient.getSyndFeed(url);
        //then
        System.out.println(syndFeed.getLink());
        assertThat(syndFeed).isNotNull();
    }
}