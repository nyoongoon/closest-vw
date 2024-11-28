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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.NOT_EXISTS_BLOG;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSchedulerService {
    private static final int PAGE_SIZE = 100;
    private final BlogFactory blogFactory;
    private final BlogRepository blogRepository;
    private final RssFeedClient rssFeedClient;

    @Transactional(readOnly = true)
    @Scheduled(fixedDelay = 10000) // todo 이거 이렇게 해놓으면 비동시 작업 끝나기 전에 재시작 될듯?
    public void pollingUpdatedBlogs() {
        int page = 0;
        boolean hasMore = true;

        while (hasMore) { // FIXME 무한 루프 돌고 있음... .. post 중복으로 계속 들어감....
            Page<BlogRoot> blogPage = blogRepository.findAll(PageRequest.of(page, PAGE_SIZE));
            List<BlogRoot> blogRoots = blogPage.getContent();
            hasMore = blogPage.hasNext();

            // 비동기 작업 수집 // todo 분리 // 스레드 기다려보면서 부하 검증..? //jmeter같은 부하테스트가 필요하다..
            List<CompletableFuture<Void>> futures = blogRoots.stream()
                    .map(blogRoot -> rssFeedClient.polling(blogRoot.getBlogInfo().getRssUrl())
                            .thenAccept(syndFeed -> {
                                try {
                                    updateBlogBySyndFeed(blogRoot.getId(), syndFeed);
                                } catch (MalformedURLException e) {
                                    //로그만 남겨도 됨?
                                } catch (URISyntaxException e) {
                                    //로그만 남겨도 됨?
                                }
                            })
                            .exceptionally(ex -> {
                                log.error("Error processing blogRoot: {}", blogRoot.getId(), ex);
                                return null;
                            }))
                    .toList();

            // 모든 작업 완료 대기
//            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            page++;
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBlogBySyndFeed(Long blogId, SyndFeed syndFeed) throws MalformedURLException, URISyntaxException {
        BlogRoot blogRoot = blogRepository.findById(blogId).orElseThrow(() -> new IllegalStateException(NOT_EXISTS_BLOG));
        log.info("updateBlogBySyndFeed() - RssUrl : {}", blogRoot.getBlogInfo().getRssUrl());
        BlogRoot recentBlogRoot = blogFactory.createRecentBlogRoot(blogRoot.getBlogInfo().getRssUrl(), syndFeed);
        blogRoot.updateBlogRoot(recentBlogRoot);
        blogRepository.save(blogRoot);
    }

}
