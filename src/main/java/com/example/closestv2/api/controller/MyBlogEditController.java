package com.example.closestv2.api.controller;

import com.example.closestv2.api.MyBlogEditApi;
import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogEditUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyBlogEditController implements MyBlogEditApi {
    private final MyBlogEditUsecase myBlogEditUsecase;

    @Override
    public ResponseEntity<Void> myBlogStatusPatch(MyBlogStatusPatchRequest request) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        myBlogEditUsecase.editMyBlogStatusMessage((long) principal, toServiceRequest(request));

        return ResponseEntity.ok().build();
    }

    private MyBlogStatusPatchServiceRequest toServiceRequest(MyBlogStatusPatchRequest request){
        return new MyBlogStatusPatchServiceRequest(request.getMessage());
    }
}
