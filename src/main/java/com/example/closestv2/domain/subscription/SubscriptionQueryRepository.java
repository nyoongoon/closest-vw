package com.example.closestv2.domain.subscription;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionQueryRepository {
    List<SubscriptionRoot> findByMemberIdVisitCountDesc(long memberId, int page, int size);

    List<SubscriptionRoot> findByMemberIdPublishedDateTimeDesc(long memberId, int page, int size);
}
