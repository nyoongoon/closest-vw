package com.example.closestv2.infrastructure.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher; // initializing
    }

    public static void raise(Object event) {
        if (publisher == null) {
            throw new IllegalStateException();
        }
        publisher.publishEvent(event);
    }
}
