package com.example.closestv2.api.controller;

import com.example.closestv2.api.PostLikeApi;
import com.example.closestv2.api.service.model.request.PostsLikePostServiceRequest;
import com.example.closestv2.api.usecases.PostLikeUsecase;
import com.example.closestv2.models.PostsLikePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.WRONG_POST_URL_FORMAT;

@RestController
@RequiredArgsConstructor
public class PostLikeController implements PostLikeApi {
    private PostLikeUsecase postLikeUsecase;

    @Override
    public ResponseEntity<Void> postsLikePost(PostsLikePostRequest request) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        PostsLikePostServiceRequest serviceRequest = toServiceRequest((long) principal, request);
        postLikeUsecase.likePost(serviceRequest);
        return ResponseEntity.ok().build();
    }

    private PostsLikePostServiceRequest toServiceRequest(long memberId, PostsLikePostRequest request) {
        try {
            return PostsLikePostServiceRequest.builder()
                    .memberId(memberId)
                    .postUrl(request.getPostUri().toURL())
                    .build();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(WRONG_POST_URL_FORMAT);
        }
    }
}
