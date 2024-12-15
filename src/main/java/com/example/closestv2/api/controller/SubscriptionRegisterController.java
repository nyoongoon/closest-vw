package com.example.closestv2.api.controller;

import com.example.closestv2.api.SubscriptionRegisterApi;
import com.example.closestv2.api.service.model.request.SubscriptionsPostServiceRequest;
import com.example.closestv2.api.usecases.SubscriptionRegisterUsecase;
import com.example.closestv2.models.SubscriptionsPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.WRONG_RSS_URL_FORMAT;

@RestController
@RequiredArgsConstructor
public class SubscriptionRegisterController implements SubscriptionRegisterApi {
    private SubscriptionRegisterUsecase subscriptionRegisterUsecase;

    @Override
    public ResponseEntity<Void> subscriptionsPost(SubscriptionsPostRequest subscriptionsPostRequest) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        SubscriptionsPostServiceRequest serviceRequest = toServiceRequest((long) principal, subscriptionsPostRequest);
        subscriptionRegisterUsecase.registerSubscription(serviceRequest);
        return ResponseEntity.ok().build();
    }

    private SubscriptionsPostServiceRequest toServiceRequest(long memberId, SubscriptionsPostRequest subscriptionsPostRequest) {
        URL rssUrl;
        try {
            rssUrl = subscriptionsPostRequest.getRssUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(WRONG_RSS_URL_FORMAT);
        }

        return SubscriptionsPostServiceRequest.builder()
                .memberId(memberId)
                .rssUrl(rssUrl)
                .build();
    }

    @Override
    public ResponseEntity<Void> subscriptionsSubscriptionsIdDelete(Long subscriptionsId) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        subscriptionRegisterUsecase.unregisterSubscription((long) principal, subscriptionsId);
        return ResponseEntity.ok().build();
    }
}
