package com.example.closestv2.config;

import com.example.closestv2.infrastructure.event.Events;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventInitializer(){
        return ()-> Events.setPublisher(applicationContext);
    }
}
