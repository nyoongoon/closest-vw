package com.example.closestv2.api.controller;

import com.example.closestv2.api.PostLikeApi;
import com.example.closestv2.models.PostsLikePostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeController implements PostLikeApi {

    @Override
    public ResponseEntity<Void> postsLikePost(PostsLikePostRequest postsLikePostRequest) {
        return null;
    }
}
