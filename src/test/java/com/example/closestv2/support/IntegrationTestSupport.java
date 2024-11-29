package com.example.closestv2.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:application.yml")
public abstract class IntegrationTestSupport {
}
