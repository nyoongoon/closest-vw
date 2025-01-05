package com.example.closestv2.infrastructure.event;

import com.example.closestv2.domain.subscription.event.SubscriptionsBlogVisitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class SubscriptionsBlogVisitListener {

    // TODO 블로그 방문 횟수 증가
    @EventListener
    public void onSubscriptionsBlogVisitEvent(SubscriptionsBlogVisitEvent event) {
        URL blogUrl = event.blogUrl();

//        blogEditService.editStatueMessage(blogUrl, statusMessage);
    }
}
