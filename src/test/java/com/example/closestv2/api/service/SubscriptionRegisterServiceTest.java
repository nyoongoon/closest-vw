package com.example.closestv2.api.service;

import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.domain.subscription.SubscriptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

class SubscriptionRegisterServiceTest {
    private final long ANY_MEMBER_ID = 1L;
    private final URL ANY_BLOG_URL = URI.create("http://example.com").toURL();

    @Mock
    private FeedClient feedClient;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    SubscriptionRegisterServiceTest() throws MalformedURLException {
    }

    void setUp() {

    }

    @Test
    @DisplayName("구독 가입 성공")
    void registerSubscription() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("FeedItem이 존재하지 않는 경우의 구독 가입 성공")
    void registerSubscriptionWithNoFeedItems() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("구독 가입 실패 - FeedClient로 Feed 조회 불가")
    void registerSubscriptionWithFailFeedClient() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("구독 삭제 성공")
    void unregisterSubscription() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("구독 삭제 실패 - 구독아이디 존재하지 않는 경우")
    void unregisterSubscriptionNotFoundSubscription() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }

    @Test
    @DisplayName("구독 삭제 실패 - 구독 정보의 memberId가 세션의 memberId와 다른 경우")
    void unregisterSubscriptionNotMatchesMemberId() {
        //given
        //when
        //then
        throw new IllegalArgumentException();
    }
}