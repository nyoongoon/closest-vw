package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.response.VisitSubscriptionResponse;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionVisitUsecase {
    VisitSubscriptionResponse visitSubscription(long subscriptionsId);
}
