package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import org.springframework.http.ResponseEntity;

public interface MyBlogStatusUsecase {
    void resetMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest);
}
