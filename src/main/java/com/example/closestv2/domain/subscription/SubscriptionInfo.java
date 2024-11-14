package com.example.closestv2.domain.subscription;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_ID_IS_REQUIRED;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.SUBSCRIPTION_VISIT_COUNT_IS_REQUIRED;

@Embeddable
@Builder(access = AccessLevel.PROTECTED)
record SubscriptionInfo(
        @NotNull(message = MEMBER_ID_IS_REQUIRED)
        Long memberId,

        @NotNull(message = SUBSCRIPTION_VISIT_COUNT_IS_REQUIRED)
        Long subscriptionVisitCount,

        String subscriptionNickName
) {
}
