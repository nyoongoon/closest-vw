package com.example.closestv2.domain.subscription;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_ID_IS_REQUIRED;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.SUBSCRIPTION_VISIT_COUNT_IS_REQUIRED;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SubscriptionInfo {
    @NotNull(message = MEMBER_ID_IS_REQUIRED)
    Long memberId;

    @NotNull(message = SUBSCRIPTION_VISIT_COUNT_IS_REQUIRED)
    Long subscriptionVisitCount;

    String subscriptionNickName;

    @Builder(access = AccessLevel.PROTECTED)
    private SubscriptionInfo(
            Long memberId,
            Long subscriptionVisitCount,
            String subscriptionNickName
    ) {
        Assert.notNull(memberId, MEMBER_ID_IS_REQUIRED);
        Assert.notNull(subscriptionVisitCount, SUBSCRIPTION_VISIT_COUNT_IS_REQUIRED);

        this.memberId = memberId;
        this.subscriptionVisitCount = subscriptionVisitCount;
        this.subscriptionNickName = subscriptionNickName;
    }
}
