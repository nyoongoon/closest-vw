package com.example.closestv2.api.controller;

import com.example.closestv2.api.SubscriptionRegisterApi;
import com.example.closestv2.models.SubscriptionsPostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionRegisterController implements SubscriptionRegisterApi {
    @Override
    public ResponseEntity<Void> subscriptionsPost(SubscriptionsPostRequest subscriptionsPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> subscriptionsSubscriptionsIdDelete(Integer subscriptionsId) {
        return null;
    }
}
