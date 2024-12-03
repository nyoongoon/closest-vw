package com.example.closestv2.infrastructure.event;

import com.example.closestv2.domain.member.event.StatusMessageChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StatusMessageEventListener {

    @EventListener
    public void onStatusMessageEvent(StatusMessageChangeEvent event) {

    }
}
