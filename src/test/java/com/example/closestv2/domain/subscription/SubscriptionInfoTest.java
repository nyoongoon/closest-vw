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
class SubscriptionInfoTest extends RepositoryTestSupport {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("구독 생성 시 구독 정보의 memberId가 null이면 에러가 발생한다.")
    void createSubscriptionBySubscriptionInfoWithNullMemberId() throws MalformedURLException {
        //given
        Long memberId = null;
        SubscriptionRoot subscriptionRoot = SubscriptionRoot.create(
                memberId,
                new URL("https://example.com/blog123"),
                "블로그 제목",
                LocalDateTime.of(2030, 1, 1, 12, 3, 31)
        );
        //expected
        assertThatThrownBy(() -> subscriptionRepository.save(subscriptionRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }
}