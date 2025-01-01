package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.response.VisitSubscriptionResponse;
import com.example.closestv2.domain.subscription.SubscriptionRepository;
import com.example.closestv2.domain.subscription.SubscriptionRoot;
import com.example.closestv2.util.url.UrlUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class SubscriptionVisitServiceTest {
    private final long ANY_SUBSCRIPTIONS_ID = 1L;
    private final long ANY_MEMBER_ID = 1L;
    private final URL ANY_BLOG_URL = UrlUtils.from("https://example.com/blog123");
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);
    private SubscriptionVisitService sut;

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ApplicationEventPublisher mockPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new SubscriptionVisitService(subscriptionRepository, mockPublisher);
    }

    private SubscriptionRoot createMock(){
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(ANY_MEMBER_ID, ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_PUBLISHED_DATE_TIME);
        ReflectionTestUtils.setField(subscriptionRoot, "id", 1L);
        when(subscriptionRepository.findById(ANY_SUBSCRIPTIONS_ID)).thenReturn(Optional.of(subscriptionRoot));
        return subscriptionRoot;
    }

    @Test
    @DisplayName("구독 방문 성공 테스트")
    void visitSubscription() {
        //given
        createMock();
        //when
        VisitSubscriptionResponse response = sut.visitSubscription(ANY_SUBSCRIPTIONS_ID);
        //then
        URL redirectUrl = response.getRedirectUrl();
        assertThat(ANY_BLOG_URL).isEqualTo(redirectUrl);
    }

    @Test
    @DisplayName("구독 방문 성공 테스트 - Subscription 조회수가 1 증가한다.")
    void visitSubscriptionIncreaseVitsitCnt() {
        //given
        SubscriptionRoot mock = createMock();
        //when
        VisitSubscriptionResponse response = sut.visitSubscription(ANY_SUBSCRIPTIONS_ID);
        //then

//        URL redirectUrl = response.getRedirectUrl()
    }

    @Test
    @DisplayName("구독 방문 실패 테스트 - 존재하지 않는 subscriptionsId")
    void visitSubscriptionFail(){
        //given
        //expected
        assertThatThrownBy(() -> sut.visitSubscription(2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("구독 방문 실패 테스트 - Subscription 조회수가 1 증가하지 않으면 에러가 발생한다.")
    void visitSubscriptionIncreaseVitsitCntFail(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("블로그 방문시 블로그 방문 이벤트 발생")
    void visitSubscriptionPublishBlogVisitEvent() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("블로그 방문시 블로그 방문 이벤트 발생하지 않으면 에러가 발생한다.")
    void visitSubscriptionPublishBlogVisitFailEvent() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("구독 포스트 방문 실패 테스트 - Subscription 조회수가 1 증가하지 않으면 에러가 발생한다.")
    void visitSubscriptionPostIncreaseVitsitCntFail(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("포스트 방문 이벤트 발생")
    void visitSubscriptionPostPublishPostVisitEvent() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }
}
