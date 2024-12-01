package com.example.closestv2.api.service;

import com.example.closestv2.models.AuthMessage;
import com.example.closestv2.usecase.BlogUsecase;
import org.springframework.stereotype.Service;

import java.net.URI;
@Service
class BlogApplicationService implements BlogUsecase {

    @Override
    public AuthMessage getBlogAuthMessageGet(URI url) {
        return null;
    }
}
