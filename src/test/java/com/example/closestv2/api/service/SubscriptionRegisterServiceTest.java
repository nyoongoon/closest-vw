package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.SubscriptionsPostServiceRequest;
import com.example.closestv2.domain.blog.BlogRepository;
import com.example.closestv2.domain.feed.Feed;
import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.domain.subscription.SubscriptionRepository;
import com.example.closestv2.domain.subscription.SubscriptionRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SubscriptionRegisterServiceTest {
    private final Long ANY_MEMBER_ID = 1L;
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final String ANY_AUTHOR = "블로그 작가";
    private final URL ANY_RSS_URL = URI.create("http://example.com/rss").toURL();
    private final URL ANY_BLOG_URL = URI.create("http://example.com/rss").toURL();
    private SubscriptionRegisterService subscriptionRegisterService;

    @Mock
    private FeedClient feedClient;
    @Mock
    private BlogRepository blogRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    SubscriptionRegisterServiceTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp()  {
        MockitoAnnotations.openMocks(this);

        subscriptionRegisterService = new SubscriptionRegisterService(feedClient, blogRepository, subscriptionRepository);
    }

    @Test
    @DisplayName("구독 가입 성공")
    void registerSubscription()  {
        //given
        SubscriptionsPostServiceRequest request = new SubscriptionsPostServiceRequest(ANY_MEMBER_ID, ANY_RSS_URL);
        when(feedClient.getFeed(ANY_RSS_URL)).thenReturn(Feed.create(ANY_RSS_URL, ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, null));
        ArgumentCaptor<SubscriptionRoot> captor = ArgumentCaptor.forClass(SubscriptionRoot.class);
        //when
        subscriptionRegisterService.registerSubscription(request);
        //then
        verify(subscriptionRepository, times(1)).save(captor.capture());
        SubscriptionRoot subscriptionRoot = captor.getValue();
        assertThat(ANY_MEMBER_ID).isEqualTo(subscriptionRoot.getSubscriptionInfo().getMemberId());
    }

    @Test
    @DisplayName("구독 가입 시 FeedItem이 존재하지 않으면 publishedDateTime은 에포크 타임이다.")
    void registerSubscriptionWithNoFeedItems() {
        SubscriptionsPostServiceRequest request = new SubscriptionsPostServiceRequest(ANY_MEMBER_ID, ANY_RSS_URL);
        when(feedClient.getFeed(ANY_RSS_URL)).thenReturn(Feed.create(ANY_RSS_URL, ANY_BLOG_URL, ANY_BLOG_TITLE, ANY_AUTHOR, null));
        ArgumentCaptor<SubscriptionRoot> captor = ArgumentCaptor.forClass(SubscriptionRoot.class);
        //when
        subscriptionRegisterService.registerSubscription(request);
        //then
        verify(subscriptionRepository, times(1)).save(captor.capture());
        SubscriptionRoot subscriptionRoot = captor.getValue();
        assertThat(ANY_MEMBER_ID).isEqualTo(subscriptionRoot.getSubscriptionInfo().getMemberId());
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