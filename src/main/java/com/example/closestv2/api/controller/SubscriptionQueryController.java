package com.example.closestv2.api.controller;

import com.example.closestv2.api.SubscriptionQueryApi;
import com.example.closestv2.models.SubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionQueryController implements SubscriptionQueryApi {
    @Override
    public ResponseEntity<List<SubscriptionResponse>> subscriptionsBlogsCloseGet() {
        return null;
    }

    @Override
    public ResponseEntity<List<SubscriptionResponse>> subscriptionsBlogsGet(Integer page, Integer size) {
        return null;
    }
}
