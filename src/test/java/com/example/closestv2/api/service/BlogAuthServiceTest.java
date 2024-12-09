package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogAuthCode;
import com.example.closestv2.domain.blog.BlogAuthenticator;
import com.example.closestv2.domain.feed.Feed;
import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.infrastructure.domain.blog.BlogAuthCodeRepository;
import com.example.closestv2.models.AuthMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class BlogAuthServiceTest {
    private BlogAuthService sut;
    @Mock
    private FeedClient feedClient;
    @Mock
    private BlogAuthenticator blogAuthenticator;
    @Mock
    private BlogAuthCodeRepository blogAuthCodeRepository;

    private final Long ANY_MEMBER_ID = 1L;
    private final URI ANY_RSS_URI = URI.create("http://www.example.com/rss");
    private final URI ANY_BLOG_URI = URI.create("http://www.example.com");
    private final URI WRONG_RSS_URI = URI.create("http://www.example.com/fail");
    private final String ANY_AUTH_CODE = "ABC123";
    private final String ANY_BLOG_TITLE = "ABC123";
    private final String WRONG_BLOG_TITLE = "123ABC";
    private final String ANY_AUTHOR = "블로그 작가";

    BlogAuthServiceTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        MockitoAnnotations.openMocks(this);

        Feed feed = Feed.create(ANY_RSS_URI.toURL(), ANY_BLOG_URI.toURL(), ANY_BLOG_TITLE, ANY_AUTHOR, null);
        when(feedClient.getFeed(ANY_RSS_URI.toURL())).thenReturn(feed);
        when(feedClient.getFeed(WRONG_RSS_URI.toURL())).thenThrow(new IllegalStateException());

        BlogAuthCode blogAuthCode = new BlogAuthCode(ANY_MEMBER_ID, ANY_RSS_URI.toURL(), ANY_AUTH_CODE);
        when(blogAuthenticator.createAuthCode(ANY_MEMBER_ID, ANY_RSS_URI.toURL())).thenReturn(new BlogAuthCode(ANY_MEMBER_ID, ANY_RSS_URI.toURL(), ANY_AUTH_CODE));
        when(blogAuthenticator.authenticate(blogAuthCode, ANY_BLOG_TITLE)).thenReturn(true);
        when(blogAuthenticator.authenticate(blogAuthCode, WRONG_BLOG_TITLE)).thenReturn(false);

        when(blogAuthCodeRepository.save(blogAuthCode)).thenReturn(blogAuthCode);
        when(blogAuthCodeRepository.findByMemberId(ANY_MEMBER_ID)).thenReturn(blogAuthCode);

        sut = new BlogAuthService(feedClient, blogAuthenticator, blogAuthCodeRepository);
    }

    @Test
    @DisplayName("memberId와 rssUri로 인증 메시지를 요청한다. - 인증 메시지 생성 및 저장 후 리턴")
    void getBlogAuthMessage()  {
        //given
        //when
        AuthMessageResponse response = sut.getBlogAuthMessage(ANY_MEMBER_ID, ANY_RSS_URI);
        //then
        BlogAuthCode blogAuthCode = blogAuthCodeRepository.findByMemberId(ANY_MEMBER_ID);
        assertThat(response.getAuthMessage()).isEqualTo(blogAuthCode.authMessage());
    }

    @Test
    @DisplayName("rss가 조회되지 않은 rssUrl을 요청 시 예외가 발생한다.")
    void createAuthCodeWithNotRssUrl(){
        //given
        //expected
        assertThatThrownBy(() -> sut.getBlogAuthMessage(ANY_MEMBER_ID, WRONG_RSS_URI))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("저장한 경우가 없는 memberId로 인증 요청 시 에러가 발생하낟.")
    void verifyBlogAuthMessageWithNotSavedMemberId(){
        //given
        //when
        //then
    }
}