package com.example.closestv2.domain.subscription;

import jakarta.persistence.Embeddable;

@Embeddable
public record SubscriptionInfo(
        Long memberId,
        String blogTitle,
        String subscriptionNickName,
        Long subscriptionVisitCount


        ) {
}
