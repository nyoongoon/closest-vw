package com.example.closestv2.api.usecases;


import com.example.closestv2.api.service.model.request.SubscriptionsPostServiceRequest;

public interface SubscriptionRegisterUsecase {
    void registerSubscription(SubscriptionsPostServiceRequest serviceRequest);
    void unregisterSubscription(SubscriptionsPostServiceRequest serviceRequest);
}
