package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogStatusUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MyBlogStatusService implements MyBlogStatusUsecase {
    @Override
    public ResponseEntity<Void> resetMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest) {

        return null;
    }
}
