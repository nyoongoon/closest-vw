package com.example.closestv2.domain.subscription.event;

public record SubscriptionsPostVisitEvent(
        long subscriptionsId,
        long blogId,
        long postId
) {
}
