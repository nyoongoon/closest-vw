package com.example.closestv2.api.service;

import com.example.closestv2.api.usecases.SubscriptionQueryUsecase;
import com.example.closestv2.models.SubscriptionResponse;

import java.util.List;

public class SubscriptionQueryService implements SubscriptionQueryUsecase {
    @Override
    public List<SubscriptionResponse> getCloseSubscriptionList(long memberId) {
        return List.of();
    }

    @Override
    public List<SubscriptionResponse> getCloseSubscriptionList(long memberId, int page, int size) {
        return List.of();
    }
}
