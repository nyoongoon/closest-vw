package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogSchedulerService {
    private final BlogFactory blogFactory;
    private final BlogRepository blogRepository;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void pollingUpdatedBlogs() throws MalformedURLException, URISyntaxException {
        List<BlogRoot> blogRoots = blogRepository.findAll();
        for (BlogRoot blogRoot : blogRoots) {
            pollingIfUpdated(blogRoot);
        }
    }

    private void pollingIfUpdated(BlogRoot blogRoot) throws MalformedURLException, URISyntaxException {
        BlogRoot recentBlogRoot = blogFactory.createRecentBlogRoot(blogRoot.getBlogInfo().getBlogUrl());
        boolean isBlogUpdated = blogRoot.isBlogUpdated(recentBlogRoot);
        if(!isBlogUpdated){
            return;
        }
        blogRoot.updateBlogRoot(recentBlogRoot);
    }
}
