package com.example.closestv2.domain.subscription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionInfoTest {
    private final Long ANY_MEMBER_ID = 1L;
    private final Long ANY_SUBSCRIPTION_VISIT_COUNT = 0L;

    private SubscriptionInfo sut;
    private SubscriptionInfo.SubscriptionInfoBuilder builder;

    @BeforeEach
    void setUp() {
        builder = SubscriptionInfo.builder()
                .memberId(ANY_MEMBER_ID)
                .subscriptionVisitCount(ANY_SUBSCRIPTION_VISIT_COUNT);
    }

    @Test
    @DisplayName("SubscriptionInfo 생성 예외 케이스 - 필수값 검증")
    void createSubscriptionInfoFailTest() {
        assertThatThrownBy(() -> sut = builder.memberId(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.subscriptionVisitCount(null).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("SubscriptionInfo 생성 성공 케이스")
    void createSubscriptionInfoSuccessTest() {
        sut = builder.build();
        assertThat(sut.getMemberId()).isEqualTo(ANY_MEMBER_ID);
        assertThat(sut.getSubscriptionVisitCount()).isEqualTo(ANY_SUBSCRIPTION_VISIT_COUNT);
    }
}