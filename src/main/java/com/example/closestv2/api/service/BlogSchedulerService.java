package com.example.closestv2.api.service;

import com.example.closestv2.clients.RssFeedClient;
import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSchedulerService { // 이런 서비스 레이어의 테스트 -> 통합테스트 -> mock을 잘 안쓰나..?
    private final BlogFactory blogFactory;
    private final BlogRepository blogRepository;
    private final RssFeedClient rssFeedClient;

    private static final int PAGE_SIZE = 100;

    @Transactional
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

    /**
     * @Async 하면 테스트 코드도 비동기로 처리?
     *
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pollingIfUpdated(BlogRoot blogRoot) {
        try {
            URL rssUrl = blogRoot.getBlogInfo().getRssUrl();
            SyndFeed syndFeed = rssFeedClient.getSyndFeed(rssUrl);
            BlogRoot recentBlogRoot = blogFactory.createRecentBlogRoot(rssUrl, syndFeed);
            boolean isBlogUpdated = blogRoot.isBlogUpdated(recentBlogRoot);

            if (isBlogUpdated) {
                blogRoot.updateBlogRoot(recentBlogRoot);
                blogRepository.save(blogRoot);
            }
        } catch (MalformedURLException | URISyntaxException e) {
            // 에러 로그 처리, 로깅 사용 권장
            log.error("블로그 업데이트 중 에러 발생 - url :{}", blogRoot.getBlogInfo().getBlogUrl().toString());
            log.error("에러 메시지 : {}", e.getMessage());
        }
    }
}
