package com.example.closestv2.api.controller;


import com.example.closestv2.api.BlogsApi;
import com.example.closestv2.models.AuthMessage;
import com.example.closestv2.models.BlogAuthVerificationPostRequest;
import com.example.closestv2.models.BlogsPostsLikePostRequest;
import com.example.closestv2.models.BlogsStatusMyPatchRequest;
import com.example.closestv2.usecase.BlogUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
class BlogController implements BlogsApi {

//    private final BlogUsecase blogUsecase;

    @Override
    public ResponseEntity<AuthMessage> blogAuthMessageGet(URI url) {
        // validation
//        AuthMessage blogAuthMessageGet = blogUsecase.getBlogAuthMessageGet(url);
        return ResponseEntity.ok(null);
    }
    // Client >>*>> generated OpenAPI >>>*>> Controller >>>>> Service
    @Override
    public ResponseEntity<Void> blogAuthVerificationPost(BlogAuthVerificationPostRequest blogAuthVerificationPostRequest) {
        //유효하지 않는 URL 이 들어올 경우 400 > blogAuthVerificationPostRequest.getUrl()

        return null;
    }

    @Override
    public ResponseEntity<Void> blogsPostsLikePost(BlogsPostsLikePostRequest blogsPostsLikePostRequest) {

        return null;
    }

    @Override
    public ResponseEntity<Void> blogsStatusMyPatch(BlogsStatusMyPatchRequest blogsStatusMyPatchRequest) {

        return null;
    }
}
