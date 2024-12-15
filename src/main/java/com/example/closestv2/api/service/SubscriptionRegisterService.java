package com.example.closestv2.api.service;


import com.example.closestv2.api.service.model.request.SubscriptionsPostServiceRequest;
import com.example.closestv2.api.usecases.SubscriptionRegisterUsecase;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.feed.Feed;
import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.domain.subscription.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionRegisterService implements SubscriptionRegisterUsecase {
    private FeedClient feedClient;
    private BlogRepository blogRepository;
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void registerSubscription(SubscriptionsPostServiceRequest serviceRequest) {
        long memberId = serviceRequest.getMemberId();
        URL rssUrl = serviceRequest.getRssUrl();

        BlogRoot blogRoot;
        Optional<BlogRoot> blogRootOptional = blogRepository.findByBlogInfoRssUrl(rssUrl);

        if (blogRootOptional.isPresent()) {
            blogRoot = blogRootOptional.get();
        } else {
            Feed feed = feedClient.getFeed(rssUrl);
            blogRoot = feed.toBlogRoot();
        }


        //TODO Feed가 Blog로 변환될 때 FeedItem의 최신 publishedDateTime이 Feed의 publishedDateTime이 된다..
        //TODO FeedItem없는 경우 에포크타임으로
        //TODO BlogRoot의 로직과 겹치는거 같은데...
        //TODO BlogRoot의 publishedDateTime 생성 로직을 Feed로 옮기기??
        LocalDateTime publishedDateTime = blogRoot.getBlogInfo().getPublishedDateTime();
//        SubscriptionRoot.create(memberId, rssUrl,  feed.getBlogTitle(), feed);


    }

    @Override
    public void unregisterSubscription(long memberId, long subscriptionId) {

    }
}
