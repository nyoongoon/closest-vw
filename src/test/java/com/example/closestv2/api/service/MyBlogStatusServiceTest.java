package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import com.example.closestv2.domain.member.MyBlog;
import com.example.closestv2.domain.member.event.StatusMessageEditEvent;
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
import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MyBlogStatusServiceTest {
    private final URL ANY_BLOG_URL = URI.create("http://example.com").toURL();
    private final String ANY_STATUS_MESSAGE = "변경된 상태 메시지";
    private MyBlogEditService myBlogStatusService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ApplicationEventPublisher mockPublisher;

    MyBlogStatusServiceTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myBlogStatusService = new MyBlogEditService(memberRepository);
        Events.setPublisher(mockPublisher);
    }

    @Test
    @DisplayName("MyBlog의 상태메시지를 변경 요청 시 새로운 상태메시지로 업데이트한다.")
    void resetMyBlogStatusMessage() {
        //given
        saveMemberRoot();
        long memberId = 1L;
        MyBlogStatusPatchServiceRequest serviceRequest = new MyBlogStatusPatchServiceRequest(ANY_STATUS_MESSAGE);
        //when
        myBlogStatusService.editMyBlogStatusMessage(memberId, serviceRequest);

        //then
        MemberRoot found = memberRepository.findById(1L).orElseThrow();
        MyBlog myBlog = found.getMyBlog();
        assertThat(myBlog.getStatusMessage()).isEqualTo(ANY_STATUS_MESSAGE);
    }

    @Test
    @DisplayName("MyBlog의 상태메시지를 변경 요청 시 상태메시지 변경 이벤트를 발행한다.")
    void resetMyBlogStatusMessageWithPublishingEvent()  {
        // given
        saveMemberRoot();
        long memberId = 1L;
        MyBlogStatusPatchServiceRequest serviceRequest = new MyBlogStatusPatchServiceRequest(ANY_STATUS_MESSAGE);
        ArgumentCaptor<StatusMessageEditEvent> captor = ArgumentCaptor.forClass(StatusMessageEditEvent.class);
        // when
        myBlogStatusService.editMyBlogStatusMessage(memberId, serviceRequest);
        // then
        verify(mockPublisher, times(1)).publishEvent(captor.capture());
        StatusMessageEditEvent event = captor.getValue();
        assertThat(event.blogUrl()).isEqualTo(ANY_BLOG_URL);
        assertThat(event.statusMessage()).isEqualTo(ANY_STATUS_MESSAGE); //검증하기..
    }

    private void saveMemberRoot() {
        MemberRoot memberRoot = MemberRoot.create("abc@naver.com", "Ab1234!!", "별명123");
        memberRoot.saveMyBlog(ANY_BLOG_URL, 0L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(memberRoot));
    }
}