package com.example.closestv2.infrastructure.event;

import com.example.closestv2.api.service.BlogEditService;
import com.example.closestv2.api.service.MyBlogSaveService;
import com.example.closestv2.domain.blog.event.MyBlogSaveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MyBlogSaveListenerTest {
    private final Long ANY_MEMBER_ID = 1L;
    private final URL ANY_BLOG_URI = URI.create("http://www.example.com").toURL();

    @Mock
    private MyBlogSaveService myBlogSaveService;

    private AnnotationConfigApplicationContext context;

    MyBlogSaveListenerTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Spring 컨텍스트 설정
        context = new AnnotationConfigApplicationContext();
        context.registerBean(MyBlogSaveService.class, () -> myBlogSaveService); // Mock 주입
        context.registerBean(MyBlogSaveListener.class, () -> new MyBlogSaveListener(myBlogSaveService));
        context.refresh();
        Events.setPublisher(context);
    }

    @Test
    @DisplayName("MyBlog 저장 이벤트가 발행되면 해당 이벤트를 수신하고 응용 서비스를 호출한다.")
    void onMyBlogSaveEvent() {
        //given
        MyBlogSaveEvent event = new MyBlogSaveEvent(ANY_MEMBER_ID, ANY_BLOG_URI);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<URL> urlCaptor = ArgumentCaptor.forClass(URL.class);
        //when
        Events.raise(event);
        // then
        verify(myBlogSaveService, times(1)).saveMyBlog(idCaptor.capture(), urlCaptor.capture());
        assertThat(idCaptor.getValue()).isEqualTo(ANY_MEMBER_ID);
        assertThat(urlCaptor.getValue()).isEqualTo(ANY_BLOG_URI);
    }
}