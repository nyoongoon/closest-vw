package com.example.closestv2.api.controller;

import com.example.closestv2.api.MyBlogApi;
import com.example.closestv2.api.usecases.MyBlogUsecase;
import com.example.closestv2.models.MyBlogStatusPatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyBlogController  implements MyBlogApi {
    private final MyBlogUsecase myBlogUsecase;

    @Override
    public ResponseEntity<Void> myBlogStatusPatch(MyBlogStatusPatchRequest myBlogStatusPatchRequest) {
        return null;
    }
}
