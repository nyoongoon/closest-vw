package com.example.closestv2.domain;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher){
        Events.publisher = publisher;
    }

    public static void raise(Object event){
        if(publisher != null){
            publisher.publishEvent(event);
        }
    }
}
