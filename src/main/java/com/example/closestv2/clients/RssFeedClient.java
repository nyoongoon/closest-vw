package com.example.closestv2.clients;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class RssFeedClient {
    public SyndFeed getSyndFeed(URL rssUrl) {
        try {
            XmlReader reader = new XmlReader(rssUrl);
            return new SyndFeedInput().build(reader);
        } catch (FeedException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
