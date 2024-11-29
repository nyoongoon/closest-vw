package com.example.closestv2.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //실제 포트에 띄움
@PropertySource("classpath:application.yml")
public abstract class IntegrationTestSupport {
}
