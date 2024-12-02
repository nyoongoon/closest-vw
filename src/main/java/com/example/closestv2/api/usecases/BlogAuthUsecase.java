package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.request.BlogAuthVerificationPostServiceRequest;
import com.example.closestv2.models.AuthMessage;

import java.net.URI;

public interface BlogAuthUsecase {
    AuthMessage getBlogAuthMessage(URI url);

    void verifyBlogAuthMessage(BlogAuthVerificationPostServiceRequest blogAuthVerificationPostServiceRequest);
}
