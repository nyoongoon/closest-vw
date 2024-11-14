package com.example.closestv2.domain.subscription;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionRootTest extends RepositoryTestSupport {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("구독을 방문하면 구독 방문 횟수가 증가한다.")
    void increaseVisitCount() throws MalformedURLException {
        //given
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        subscriptionRepository.save(subscriptionRoot);
        //when
        subscriptionRoot.increaseVisitCount();
        subscriptionRoot.increaseVisitCount(); // 두번 방문
        //then
        assertThat(subscriptionRoot.getSubscriptionInfo().subscriptionVisitCount())
                .isEqualTo(2L);
    }

    @Test
    @DisplayName("해당 구독의 블로그 정보 업데이트 시 최근 발생시간과 새로운 포스트 개수를 설정한다.")
    void putRecentBlogInfo() throws MalformedURLException {
        //given
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        subscriptionRepository.save(subscriptionRoot);

        LocalDateTime recent = LocalDateTime.of(2030, 1, 1, 12, 3, 59);
        Long newPostCount = 12L;
        //when
        subscriptionRoot.putRecentBlogInfo(recent, newPostCount); //발행 일자와 새로 받은 개수를 그대로 put
        //then
        SubscriptionBlog recentInfo = subscriptionRoot.getSubscriptionBlog();
        assertThat(recentInfo.publishedDateTime()).isEqualTo(LocalDateTime.of(2030, 1, 1, 12, 3, 59));
        assertThat(recentInfo.newPostCount()).isEqualTo(12L);
    }

    @Test
    @DisplayName("publishedDateTime을 새로 받을 때, 기존 시간 보다 이전 시간이면 에러가 발생한다.")
    void publishedDateTimeWithPastDateTime() throws MalformedURLException {
        //given
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31) //31초
        );
        subscriptionRepository.save(subscriptionRoot);
        LocalDateTime past = LocalDateTime.of(2030, 1, 1, 12, 3, 30); //30초
        //expected
        assertThatThrownBy(() -> subscriptionRoot.putRecentBlogInfo(past, 12L))  //새로 받은 개수를 그대로 put
                .isInstanceOf(IllegalArgumentException.class); //에러 타입 수정하기..
    }

    @Test
    @DisplayName("Subscription NickName을 변경할 수 있다.")
    void editSubscriptionNickName() throws MalformedURLException {
        //given
        String editNickName = "수정된 닉네임";
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31) //31초
        );
        subscriptionRepository.save(subscriptionRoot);
        //when
        subscriptionRoot.editSubscriptionNickName(editNickName);
        //then
        assertThat(subscriptionRoot.getSubscriptionInfo().subscriptionNickName())
                .isEqualTo("수정된 닉네임");
    }
}