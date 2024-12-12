package com.example.closestv2.support;

import com.example.closestv2.infrastructure.domain.subscription.SubscriptionQueryDslQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepositoryTestConfiguration {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SubscriptionQueryDslQueryRepository subscriptionQueryDslQueryRepository() {
        return new SubscriptionQueryDslQueryRepository(jpaQueryFactory());
    }
}
