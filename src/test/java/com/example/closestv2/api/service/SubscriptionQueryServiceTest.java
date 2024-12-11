package com.example.closestv2.api.service;

import com.example.closestv2.domain.subscription.SubscriptionQueryRepository;
import com.example.closestv2.domain.subscription.SubscriptionRoot;
import com.example.closestv2.models.SubscriptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class SubscriptionQueryServiceTest {
    private final Long ANY_MEMBER_ID_1 = 1L;
    private final Long ANY_MEMBER_ID_2 = 2L;
    private final String ANY_BLOG_LINK = "https://example.com/";
    private final String ANY_BLOG_TITLE = "블로그 제목";
    private final LocalDateTime ANY_PUBLISHED_DATE_TIME = LocalDateTime.of(2022, 1, 1, 12, 3, 31);

    private SubscriptionQueryService sut;

    @Mock
    private SubscriptionQueryRepository subscriptionQueryRepository;

    SubscriptionQueryServiceTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        MockitoAnnotations.openMocks(this);
        List<SubscriptionRoot> cloests = new ArrayList<>();
        LocalDateTime publishedDateTime = LocalDateTime.of(2020, 02, 20, 20, 20, 20);
        int visitCount = 100;
        for (int i = 1; i <= 20; i++) {
            SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(ANY_MEMBER_ID_1, URI.create(ANY_BLOG_LINK + i).toURL(), ANY_BLOG_TITLE, ANY_PUBLISHED_DATE_TIME);
            subscriptionRoot.putRecentBlogInfo(publishedDateTime.minusSeconds(i), visitCount - i);
            cloests.addLast(subscriptionRoot);
        }
        List<SubscriptionRoot> paging = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            paging.add(
                    SubscriptionRoot.create(ANY_MEMBER_ID_1, URI.create(ANY_BLOG_LINK + i).toURL(), ANY_BLOG_TITLE, ANY_PUBLISHED_DATE_TIME)
            );
        }

        when(subscriptionQueryRepository.findByMemberIdVisitCountDesc(ANY_MEMBER_ID_1, 0, 20)).thenReturn(cloests);
        when(subscriptionQueryRepository.findByMemberIdVisitCountDesc(ANY_MEMBER_ID_1, 0, 10)).thenReturn(paging);
        when(subscriptionQueryRepository.findByMemberIdVisitCountDesc(ANY_MEMBER_ID_2, 0, 20)).thenReturn(new ArrayList<>());
        when(subscriptionQueryRepository.findByMemberIdVisitCountDesc(ANY_MEMBER_ID_2, 0, 10)).thenReturn(new ArrayList<>());

        sut = new SubscriptionQueryService(subscriptionQueryRepository);
    }


    @Test
    @DisplayName("전달받은 memberId로 구독된 블로그에서 방문횟수가 높은 20개 구독 정보를 방문횟수 내림차순으로 조회힌다.")
    void getCloseSubscriptionListHighest20SubscriptionByVisitCount() {
        //given
        //when
        List<SubscriptionResponse> closeSubscriptions = sut.getCloseSubscriptions(ANY_MEMBER_ID_1);
        //then
        assertThat(closeSubscriptions).hasSize(20);
    }

    @Test
    @DisplayName("조회 결과 구독 블로그의 개수가 0개여도 에러가 발생하지 않는다.")
    void getSubscriptionZeroNoError() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("전달받은 memberId와 페이징 정보와 블로그 제목을 오름차순한 구독 정보 페이징하여 조회힌다.")
    void getCloseSubscriptionListByPaging() {
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("페이징 조회 결과 구독 블로그 개수가 0개여도 에러가 발생하지 않는다.")
    void getSubscriptionPagingZeroNoError() {
        //given
        //when
        //then
    }
}