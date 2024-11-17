package com.example.closestv2.controller;

import com.example.closestv2.apis.BlogsApi;
import com.example.closestv2.models.AuthMessage;
import com.example.closestv2.models.BlogAuthVerificationPostRequest;
import com.example.closestv2.models.BlogsPostsLikePostRequest;
import com.example.closestv2.models.BlogsStatusMyPatchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
class BlogsController implements BlogsApi {

    @Override
    public ResponseEntity<AuthMessage> blogAuthMessageGet(URI url) {
        return null;
    }

    @Override
    public ResponseEntity<Void> blogAuthVerificationPost(BlogAuthVerificationPostRequest blogAuthVerificationPostRequest) {
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
