package com.example.closestv2.domain.feed;

import java.net.MalformedURLException;
import java.net.URL;

public interface FeedClient {
    Feed getFeed(URL rssUrl);
}
