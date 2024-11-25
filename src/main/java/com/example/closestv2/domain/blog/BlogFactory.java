package com.example.closestv2.domain.blog;

import com.rometools.rome.feed.synd.SyndFeed;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface BlogFactory {
    BlogRoot createRecentBlogRoot(SyndFeed syndFeed) throws URISyntaxException, MalformedURLException;
}
