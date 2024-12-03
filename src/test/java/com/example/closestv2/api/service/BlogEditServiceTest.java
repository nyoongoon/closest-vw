package com.example.closestv2.api.service;

import com.example.closestv2.domain.member.event.StatusMessageEditEvent;
import com.example.closestv2.infrastructure.event.Events;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class BlogEditServiceTest {
    private final URL ANY_BLOG_URL = URI.create("http://example.com").toURL();
    private final String ANY_STATUS_MESSAGE = "변경된 상태 메시지";

    @Mock
    private ApplicationEventPublisher mockPublisher;

    BlogEditServiceTest() throws MalformedURLException {
    }

    @Test
    @DisplayName("")
    void editStatueMessage(){
        //given

        Events.raise(new StatusMessageEditEvent(ANY_BLOG_URL, ANY_STATUS_MESSAGE));
        //when
        //then
        throw new IllegalStateException();
    }
}