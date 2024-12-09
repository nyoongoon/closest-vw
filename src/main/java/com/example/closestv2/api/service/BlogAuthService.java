package com.example.closestv2.api.service;

import com.example.closestv2.api.usecases.BlogAuthUsecase;
import com.example.closestv2.domain.blog.BlogAuthCode;
import com.example.closestv2.domain.blog.BlogAuthenticator;
import com.example.closestv2.domain.feed.Feed;
import com.example.closestv2.domain.feed.FeedClient;
import com.example.closestv2.infrastructure.domain.blog.BlogAuthCodeRepository;
import com.example.closestv2.models.AuthMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.SERVER_ERROR;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.WRONG_RSS_URL_FORMAT;

@Service
@RequiredArgsConstructor
public class BlogAuthService implements BlogAuthUsecase {
    private final FeedClient feedClient;
    private final BlogAuthenticator blogAuthenticator;
    private final BlogAuthCodeRepository blogAuthCodeRepository;

    @Override
    public AuthMessageResponse getBlogAuthMessage(long memberId, URI rssUri) {
        try {
            Feed feed = feedClient.getFeed(rssUri.toURL());
            BlogAuthCode blogAuthCode = blogAuthenticator.createAuthCode(memberId, feed.getRssUrl());
            blogAuthCodeRepository.save(blogAuthCode);
            return new AuthMessageResponse().authMessage(blogAuthCode.authMessage());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(WRONG_RSS_URL_FORMAT);
        } catch (Exception e){
            throw new IllegalStateException(SERVER_ERROR);
        }
    }

    @Override
    public void verifyBlogAuthMessage(long memberId) {

    }
}
