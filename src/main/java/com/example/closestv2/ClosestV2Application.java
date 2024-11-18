package com.example.closestv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClosestV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ClosestV2Application.class, args);
    }

}
