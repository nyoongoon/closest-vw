package com.example.closestv2.api.controller;

import com.example.closestv2.api.BlogAuthApi;
import com.example.closestv2.api.service.model.request.BlogAuthVerificationPostServiceRequest;
import com.example.closestv2.api.usecases.BlogAuthUsecase;
import com.example.closestv2.models.AuthMessageResponse;
import com.example.closestv2.models.BlogAuthVerificationPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BlogAuthController implements BlogAuthApi {
    private final BlogAuthUsecase blogAuthUsecase;

    @Override
    public ResponseEntity<AuthMessageResponse> blogAuthMessageGet(URI url) {
        AuthMessageResponse blogAuthMessage = blogAuthUsecase.getBlogAuthMessage(url);
        return ResponseEntity.ok(blogAuthMessage);
    }

    @Override
    public ResponseEntity<Void> blogAuthVerificationPost(BlogAuthVerificationPostRequest request) {
        BlogAuthVerificationPostServiceRequest serviceRequest = toServiceRequest(request);
        blogAuthUsecase.verifyBlogAuthMessage(serviceRequest);
        return ResponseEntity.ok().build();
    }

    private BlogAuthVerificationPostServiceRequest toServiceRequest(BlogAuthVerificationPostRequest request){
        return new BlogAuthVerificationPostServiceRequest(request.getUrl());
    }
}
