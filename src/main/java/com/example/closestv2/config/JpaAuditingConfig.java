package com.example.closestv2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // webMvcTest로 인해 분리
@Configuration
public class JpaAuditingConfig {
}
