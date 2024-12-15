package com.example.closestv2.infrastructure.domain.feed;

import com.example.closestv2.domain.feed.Feed;
import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.domain.feed.FeedItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.WRONG_RSS_URL_FORMAT;

@Slf4j
@Component
public class RssFeedClient implements FeedClient {
    @Override
    public Feed getFeed(URL rssUrl) {
        SyndFeed syndFeed = getSyndFeed(rssUrl);

        List<SyndEntry> entries = syndFeed.getEntries();
        List<FeedItem> feedItems = new ArrayList<>();
        for (SyndEntry entry : entries) {
            FeedItem feedItem = FeedItem.create(
                    extractUrl(entry.getLink()),
                    entry.getTitle(),
                    toLocalDateTime(entry.getPublishedDate())
            );
            feedItems.add(feedItem);
        }

        Feed feed = Feed.create(
                rssUrl,
                extractUrl(syndFeed.getLink()),
                syndFeed.getTitle(),
                syndFeed.getAuthor(),
                feedItems
        );

        return feed;
    }

    private SyndFeed getSyndFeed(URL rssUrl) {
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

    private URL extractUrl(String entry) {
        URL url;
        try {
             url = URI.create(entry).toURL();
        }catch (MalformedURLException e){
            throw new IllegalStateException(WRONG_RSS_URL_FORMAT);
        }
        return url;
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
