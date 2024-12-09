package com.example.closestv2.api.usecases;

import com.example.closestv2.models.AuthMessageResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public interface BlogAuthUsecase {
    AuthMessageResponse getBlogAuthMessage(long memberId, URI ussUri);

    void verifyBlogAuthMessage(long memberId);
}
