package com.example.closestv2.api.usecases;

import com.example.closestv2.models.SubscriptionResponse;

import java.util.List;

public interface SubscriptionQueryUsecase {
    List<SubscriptionResponse> getCloseSubscriptionList(Long memberId);
    List<SubscriptionResponse> getCloseSubscriptionList(Long memberId, Integer page, Integer size);
}
