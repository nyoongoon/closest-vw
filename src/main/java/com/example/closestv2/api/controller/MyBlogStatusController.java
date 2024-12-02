package com.example.closestv2.api.controller;

import com.example.closestv2.api.MyBlogStatusApi;
import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogStatusUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyBlogStatusController implements MyBlogStatusApi {
    private final MyBlogStatusUsecase myBlogStatusUsecase;

    @Override
    public ResponseEntity<Void> myBlogStatusPatch(MyBlogStatusPatchRequest request) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        myBlogStatusUsecase.resetMyBlogStatusMessage((long) principal, toServiceRequest(request));

        return ResponseEntity.ok().build();
    }

    private MyBlogStatusPatchServiceRequest toServiceRequest(MyBlogStatusPatchRequest request){
        return new MyBlogStatusPatchServiceRequest(request.getMessage());
    }
}
