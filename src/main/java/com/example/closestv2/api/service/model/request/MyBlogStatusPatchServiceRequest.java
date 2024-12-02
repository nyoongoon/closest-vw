package com.example.closestv2.api.service.model.request;

import lombok.Getter;

@Getter
public class MyBlogStatusPatchServiceRequest {
    private String message;

    public MyBlogStatusPatchServiceRequest(String message) {
        this.message = message;
    }
}
