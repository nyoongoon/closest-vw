package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSchedulerService { // 이런 서비스 레이어의 테스트 -> 통합테스트 -> mock을 잘 안쓰나..?
    private final BlogFactory blogFactory;
    private final BlogRepository blogRepository;

    private static final int PAGE_SIZE = 100;

    @Scheduled(fixedDelay = 5000)
    public void pollingUpdatedBlogs() {
        int page = 0;
        boolean hasMore = true;

        while(hasMore){
            Page<BlogRoot> blogPage = blogRepository.findAll(PageRequest.of(page, PAGE_SIZE));
            List<BlogRoot> blogRoots = blogPage.getContent();
            hasMore = blogPage.hasNext();

            for (BlogRoot blogRoot : blogRoots) {
                pollingIfUpdated(blogRoot);
            }
            page++;
        }
    }

    @Async
    @Transactional
    public void pollingIfUpdated(BlogRoot blogRoot) {
        try {
            BlogRoot recentBlogRoot = blogFactory.createRecentBlogRoot(blogRoot.getBlogInfo().getBlogUrl());
            boolean isBlogUpdated = blogRoot.isBlogUpdated(recentBlogRoot);

            if (isBlogUpdated) {
                blogRoot.updateBlogRoot(recentBlogRoot);
                blogRepository.save(blogRoot); // 업데이트 내용을 저장
            }
        } catch (MalformedURLException | URISyntaxException e) {
            // 에러 로그 처리, 로깅 사용 권장
            log.error("블로그 업데이트 중 에러 발생 - url :{}", blogRoot.getBlogInfo().getBlogUrl().toString());
            log.error("에러 메시지 : {}", e.getMessage());
        }
    }
}
