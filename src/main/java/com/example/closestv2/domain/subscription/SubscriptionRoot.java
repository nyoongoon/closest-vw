package com.example.closestv2.domain.subscription;

import com.example.closestv2.domain.Events;
import com.example.closestv2.domain.subscription.event.SubscriptionVisitEvent;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.RECENT_PUBLISHED_DATETIME_IS_PAST;

@Getter
@Entity
@Table(name = "subscription")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Valid
    @Embedded
    private SubscriptionInfo subscriptionInfo;

    @Valid
    @Embedded
    private SubscriptionBlog subscriptionBlog;


    @Builder(access = AccessLevel.PRIVATE)
    private SubscriptionRoot(
            SubscriptionInfo subscriptionInfo,
            SubscriptionBlog subscriptionBlog
    ) {
        this.subscriptionInfo = subscriptionInfo;
        this.subscriptionBlog = subscriptionBlog;
    }

    public static SubscriptionRoot create(
            Long memberId,
            URL blogUrl,
            String blogTitle,
            LocalDateTime publishedDateTime
    ) {
        SubscriptionInfo subscriptionInfo = SubscriptionInfo.builder()
                .memberId(memberId)
                .subscriptionVisitCount(0L)
                .build();
        SubscriptionBlog subscriptionBlog = SubscriptionBlog.builder()
                .blogUrl(blogUrl)
                .blogTitle(blogTitle)
                .publishedDateTime(publishedDateTime)
                .newPostCount(0L)
                .build();
        return SubscriptionRoot.builder()
                .subscriptionInfo(subscriptionInfo)
                .subscriptionBlog(subscriptionBlog)
                .build();
    }

    // 구독 방문 이벤트 발생 !
    public void increaseVisitCount() {
        Long plusedVisitCount = subscriptionInfo.subscriptionVisitCount() + 1;

        subscriptionInfo = SubscriptionInfo.builder()
                .memberId(subscriptionInfo.memberId())
                .subscriptionVisitCount(plusedVisitCount)
                .build();
        Events.raise(new SubscriptionVisitEvent(subscriptionBlog.blogUrl()));
    }

    public void putRecentBlogInfo(LocalDateTime publishedDateTime, Long newPostCount) {
        if (subscriptionBlog.publishedDateTime().isAfter(publishedDateTime)) {
            throw new IllegalArgumentException(RECENT_PUBLISHED_DATETIME_IS_PAST);
        }

        subscriptionBlog = SubscriptionBlog.builder()
                .blogUrl(subscriptionBlog.blogUrl())
                .blogTitle(subscriptionBlog.blogTitle())
                .publishedDateTime(publishedDateTime)
                .newPostCount(newPostCount)
                .build();
    }

    public void editSubscriptionNickName(String editNickName) {
        subscriptionInfo = SubscriptionInfo.builder()
                .memberId(subscriptionInfo.memberId())
                .subscriptionNickName(editNickName)
                .subscriptionVisitCount(subscriptionInfo.subscriptionVisitCount())
                .build();
    }
}
