package com.example.closestv2.domain.blog;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface BlogFactory {
    BlogRoot createRecentBlogRoot(URL url) throws URISyntaxException, MalformedURLException;
}
