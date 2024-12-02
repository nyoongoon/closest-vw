package com.example.closestv2.api.usecases;

import com.example.closestv2.models.MyBlogStatusPatchRequest;
import org.springframework.http.ResponseEntity;

public interface MyBlogUsecase {
    ResponseEntity<Void> resetMyBlogStatusMessage(MyBlogStatusPatchRequest myBlogStatusPatchRequest);
}
