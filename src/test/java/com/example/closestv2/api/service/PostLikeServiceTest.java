package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.PostsLikePostServiceRequest;
import com.example.closestv2.domain.likes.LikesRepository;
import com.example.closestv2.domain.likes.LikesRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PostLikeServiceTest {
    private final long ANY_MEMBER_ID = 1L;
    private final URL ANY_POST_URL = URI.create("https://example.com/1").toURL();
    private PostLikeService sut;

    @Mock
    private LikesRepository likesRepository;

    PostLikeServiceTest() throws MalformedURLException {
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        sut = new PostLikeService(likesRepository);
    }


    @Test
    @DisplayName("포스트를 좋아요 누르면 likePost라 호출되고 LikesRoot 객체가 저장된다.")
    void likePost(){
        //given
        PostsLikePostServiceRequest serviceRequest = PostsLikePostServiceRequest.builder()
                .memberId(ANY_MEMBER_ID)
                .postUrl(ANY_POST_URL)
                .build();
        ArgumentCaptor<LikesRoot> captor = ArgumentCaptor.forClass(LikesRoot.class);
        //when
        sut.likePost(serviceRequest);
        //then
        verify(likesRepository, times(1)).save(captor.capture());
        LikesRoot likesRoot = captor.getValue();
        assertThat(likesRoot.getMemberId()).isEqualTo(ANY_MEMBER_ID);
        assertThat(likesRoot.getPostUrl()).isEqualTo(ANY_POST_URL);
    }
}