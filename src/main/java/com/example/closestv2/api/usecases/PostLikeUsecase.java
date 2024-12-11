package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.request.PostsLikePostServiceRequest;

public interface PostLikeUsecase {
    void likePost(PostsLikePostServiceRequest serviceRequest);
}
