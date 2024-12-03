package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import com.example.closestv2.domain.member.MyBlog;
import com.example.closestv2.domain.member.event.StatusMessageChangeEvent;
import com.example.closestv2.infrastructure.event.Events;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MyBlogStatusServiceTest {
    private MyBlogStatusService myBlogStatusService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ApplicationEventPublisher mockPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myBlogStatusService = new MyBlogStatusService(memberRepository);
//        mockPublisher = mock(ApplicationEventPublisher.class);
        // Initializing Events with mock
        Events.setPublisher(mockPublisher);
    }

    @Test
    @DisplayName("MyBlog의 상태메시지를 변경 요청 시 새로운 상태메시지로 업데이트한다.")
    void resetMyBlogStatusMessage() throws MalformedURLException {
        //given
        long memberId = 1L;
        MyBlogStatusPatchServiceRequest serviceRequest = new MyBlogStatusPatchServiceRequest("상태 메시지입니다.");
        MemberRoot memberRoot = MemberRoot.create("abc@naver.com", "Ab1234!!", "별명123");
        memberRoot.saveMyBlog(URI.create("https://example.com/rss").toURL(), 0L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(memberRoot));

        //when
        myBlogStatusService.resetMyBlogStatusMessage(memberId, serviceRequest);

        //then
        MemberRoot found = memberRepository.findById(1L).orElseThrow();
        MyBlog myBlog = found.getMyBlog();
        assertThat(myBlog.getStatusMessage()).isEqualTo("상태 메시지입니다.");
    }

    @Test
    @DisplayName("MyBlog의 상태메시지를 변경 요청 시 상태메시지 변경 이벤트를 발행한다.")
    void resetMyBlogStatusMessageWithPublishingEvent() throws MalformedURLException {
        // given
        long memberId = 1L;
        MyBlogStatusPatchServiceRequest serviceRequest = new MyBlogStatusPatchServiceRequest("새로운 상태 메시지");
        MemberRoot memberRoot = MemberRoot.create("test@domain.com", "Password123!", "닉네임");
        memberRoot.saveMyBlog(URI.create("https://example.com/rss").toURL(), 0L);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(memberRoot));
        ArgumentCaptor<StatusMessageChangeEvent> captor = ArgumentCaptor.forClass(StatusMessageChangeEvent.class);
        // when
        myBlogStatusService.resetMyBlogStatusMessage(memberId, serviceRequest);
        // then
        verify(mockPublisher, times(1)).publishEvent(captor.capture());
        StatusMessageChangeEvent event = captor.getValue();
        assertThat(event.blogUrl().toString()).isEqualTo("https://example.com/rss");
        assertThat(event.statusMessage()).isEqualTo("새로운 상태 메시지"); //검증하기..
    }
}