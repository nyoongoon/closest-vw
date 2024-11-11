package com.example.closestv2.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class RepositoryTestConfiguration {
    @PersistenceContext
    private EntityManager entityManager;
}
