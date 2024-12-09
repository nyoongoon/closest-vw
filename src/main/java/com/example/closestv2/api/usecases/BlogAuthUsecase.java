package com.example.closestv2.api.usecases;

import com.example.closestv2.models.AuthMessageResponse;

import java.net.URI;

public interface BlogAuthUsecase {
    AuthMessageResponse createBlogAuthMessage(long memberId, URI ussUri);

    void verifyBlogAuthMessage(long memberId);
}
