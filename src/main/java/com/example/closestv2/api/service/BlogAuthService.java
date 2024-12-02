package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.BlogAuthVerificationPostServiceRequest;
import com.example.closestv2.api.usecases.BlogAuthUsecase;
import com.example.closestv2.models.AuthMessage;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class BlogAuthService implements BlogAuthUsecase {
    @Override
    public AuthMessage getBlogAuthMessage(URI url) {
        return null;
    }

    @Override
    public void verifyBlogAuthMessage(BlogAuthVerificationPostServiceRequest serviceRequest) {

    }
}
