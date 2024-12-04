package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.BlogAuthVerificationPostServiceRequest;
import com.example.closestv2.api.usecases.BlogAuthUsecase;
import com.example.closestv2.infrastructure.domain.blog.BlogAuthCodeRepository;
import com.example.closestv2.domain.blog.BlogAuthenticator;
import com.example.closestv2.models.AuthMessageResponse;
import com.example.closestv2.infrastructure.domain.feed.RssFeedClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BlogAuthService implements BlogAuthUsecase {
    private final RssFeedClient rssFeedClient; // 얘를  blogAuthenticator안에 둘것인가??
    private final BlogAuthenticator blogAuthenticator;
    private final BlogAuthCodeRepository blogAuthCodeRepository;

    @Override
    public AuthMessageResponse getBlogAuthMessage(URI url) {

        return null;
    }

    @Override
    public void verifyBlogAuthMessage(BlogAuthVerificationPostServiceRequest serviceRequest) {

    }
}
