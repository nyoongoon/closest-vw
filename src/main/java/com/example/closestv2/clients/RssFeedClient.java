package com.example.closestv2.clients;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Slf4j
@Component
public class RssFeedClient {
    public SyndFeed getSyndFeed(URL rssUrl) {
        log.info("getSyndFeed() - rssUrl : {}", rssUrl);
        try {
            XmlReader reader = new XmlReader(rssUrl);
            return new SyndFeedInput().build(reader);
        } catch (FeedException | IOException e) {
            throw new IllegalStateException(e);
        } finally {
            log.info("getSyndFeed() - rssUrl : {}", rssUrl);
        }
    }


//    @Async //동일 클래스 내에서 메서드 호출은 프록시를 거치지 않고 직접 호출되므로 @Async가 무시됨
//    public SyndFeed polling(URL rssUrl) {
//        log.info("polling() - RssUrl : {}", rssUrl);
//        try {
//            return CompletableFuture.completedFuture(getSyndFeed(rssUrl));
//        } catch (Exception ex) {
//            log.error("Error fetching RSS feed for blogRoot: {}", rssUrl, ex);
//            return CompletableFuture.failedFuture(ex);
//        }
//    }
}
