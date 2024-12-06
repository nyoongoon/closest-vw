package com.example.closestv2.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(600, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build();
        CaffeineCache caffeineCache = new CaffeineCache("blogAuthCode", cache);

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(caffeineCache));
        return cacheManager;
    }
}
