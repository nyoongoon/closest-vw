package com.example.closestv2.domain.blog;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface BlogFactory {
    BlogRoot createBlogWithPosts(URL url) throws URISyntaxException, MalformedURLException;
}
