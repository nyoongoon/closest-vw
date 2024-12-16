package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.response.VisitSubscriptionResponse;
import com.example.closestv2.api.usecases.SubscriptionVisitUsecase;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionVisitService implements SubscriptionVisitUsecase {
    @Override
    public VisitSubscriptionResponse visitSubscription(long subscriptionsId) {
        return null;
    }
}
