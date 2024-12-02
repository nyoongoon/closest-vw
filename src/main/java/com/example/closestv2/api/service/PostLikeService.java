package com.example.closestv2.api.service;

import com.example.closestv2.api.usecases.PostLikeUsecase;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class PostLikeService implements PostLikeUsecase {
    @Override
    public void likePost(Long memberId, URL url) {
    }
}
