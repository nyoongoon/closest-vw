package com.example.closestv2.api.controller;

import com.example.closestv2.api.SubscriptionRegisterApi;
import com.example.closestv2.api.service.model.request.SubscriptionsPostServiceRequest;
import com.example.closestv2.api.usecases.SubscriptionRegisterUsecase;
import com.example.closestv2.models.SubscriptionsPostRequest;
import com.example.closestv2.util.url.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

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

    private SubscriptionsPostServiceRequest toServiceRequest(long memberId, SubscriptionsPostRequest request) {
        return SubscriptionsPostServiceRequest.builder()
                .memberId(memberId)
                .rssUrl(UrlUtils.from(request.getRssUri()))
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
