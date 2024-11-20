package com.example.closestv2.domain.subscription;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
@Transactional
class SubscriptionBlogTest extends RepositoryTestSupport {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("구독 생성 시 구독 블로그 정보의 blogUrl이 null이면 에러가 발생한다.")
    void createSubscriptionBySubscriptionInfoWithNullBlogUrl() throws MalformedURLException {
        //given
        URL blogUrl = null;
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                blogUrl,
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> subscriptionRepository.save(subscriptionRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @Test
    @DisplayName("구독 생성 시 구독 정보의 blogTitle이 null이면 에러가 발생한다.")
    void createSubscriptionBySubscriptionInfoWithNullBlogTitle() throws MalformedURLException {
        //given
        String blogTitle = null;
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                blogTitle,
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> subscriptionRepository.save(subscriptionRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @Test
    @DisplayName("구독 생성 시 구독 정보의 blogTitle이 공백이면 에러가 발생한다.")
    void createSubscriptionBySubscriptionInfoWithBlankBlogTitle() throws MalformedURLException {
        //given
        String blogTitle = " ";
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                blogTitle,
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> subscriptionRepository.save(subscriptionRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @Test
    @DisplayName("구독 생성 시 구독 정보의 publishedDateTime이 공백이면 에러가 발생한다.")
    void createSubscriptionBySubscriptionInfoWithNullPublishedDateTime() throws MalformedURLException {
        //given
        LocalDateTime publishedTime = null;
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                1L,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                publishedTime
        );
        //expected
        assertThatThrownBy(() -> subscriptionRepository.save(subscriptionRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }
}