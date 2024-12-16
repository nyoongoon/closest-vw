package com.example.closestv2.api.controller;

import com.example.closestv2.api.SubscriptionVisitApi;
import com.example.closestv2.api.service.model.response.VisitSubscriptionResponse;
import com.example.closestv2.api.usecases.SubscriptionVisitUsecase;
import com.example.closestv2.util.url.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionVisitController implements SubscriptionVisitApi {
    private final SubscriptionVisitUsecase subscriptionVisitUsecase;

    //todo ResponseEntity redirect 설정
    //todo blog 방문 이벤트 -> subscription, blog visitCount 증가
    @Override
    public ResponseEntity<Void> subscriptionsSubscriptionsIdVisitGet(Integer subscriptionsId) {
        VisitSubscriptionResponse response = subscriptionVisitUsecase.visitSubscription(subscriptionsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(UrlUtils.toUri(response.getRedirectUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }


    //todo post 방문 이벤트 -> subscription, blog, post visitCount 증가
    //인자로 postId 받기?
}
