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
        //TODO 페이징이 필요할까?
        List<BlogRoot> blogRoots = blogRepository.findAll();
        for (BlogRoot blogRoot : blogRoots) {
//            pollingIfUpdated(blogRoot); // 개별 트랜잭션으로 비동기적으로 처리?
        }
    }


    private void pollingIfUpdated(BlogRoot blogRoot) throws MalformedURLException, URISyntaxException {
        //blogFactory는 외부 시스템 의존 중.. -> 테스트는 모두 mock 처리?
        BlogRoot recentBlogRoot = blogFactory.createRecentBlogRoot(blogRoot.getBlogInfo().getBlogUrl());
        boolean isBlogUpdated = blogRoot.isBlogUpdated(recentBlogRoot);
        if(!isBlogUpdated){
            return;
        }
        blogRoot.updateBlogRoot(recentBlogRoot);
    }
}
