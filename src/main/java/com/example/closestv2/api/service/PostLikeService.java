package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.PostsLikePostServiceRequest;
import com.example.closestv2.api.usecases.PostLikeUsecase;
import com.example.closestv2.domain.likes.LikesRepository;
import com.example.closestv2.domain.likes.LikesRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class PostLikeService implements PostLikeUsecase {
    private final LikesRepository likesRepository;

    @Override
    @Transactional
    public void likePost(PostsLikePostServiceRequest serviceRequest) {
        long memberId = serviceRequest.getMemberId();
        URL postUrl = serviceRequest.getPostUrl();
        LikesRoot likesRoot = LikesRoot.create(memberId, postUrl);
        likesRepository.save(likesRoot);
    }
}
