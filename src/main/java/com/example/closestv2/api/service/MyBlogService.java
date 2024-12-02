package com.example.closestv2.api.service;

import com.example.closestv2.api.usecases.MyBlogUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MyBlogService implements MyBlogUsecase {

    @Override
    public ResponseEntity<Void> resetMyBlogStatusMessage(MyBlogStatusPatchRequest myBlogStatusPatchRequest) {
        return null;
    }
}
