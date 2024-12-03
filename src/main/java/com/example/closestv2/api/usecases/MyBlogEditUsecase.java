package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;

public interface MyBlogEditUsecase {
    void editMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest);
}
