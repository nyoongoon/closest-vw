package com.example.closestv2.api.service;

import com.example.closestv2.api.usecases.SubscriptionQueryUsecase;
import com.example.closestv2.models.SubscriptionResponse;

import java.util.List;

public class SubscriptionQueryService implements SubscriptionQueryUsecase {
    @Override
    public List<SubscriptionResponse> getCloseSubscriptionList(Long memberId) {
        return List.of();
    }

    @Override
    public List<SubscriptionResponse> getCloseSubscriptionList(Long memberId, Integer page, Integer size) {
        return List.of();
    }
}
