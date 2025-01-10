package com.example.closestv2.api.controller;

import com.example.closestv2.api.MemberAuthApi;
import com.example.closestv2.api.service.model.request.MemberAuthSigninPostServiceRequest;
import com.example.closestv2.api.service.model.request.MemberAuthSignupPostServiceRequest;
import com.example.closestv2.api.usecases.MemberAuthUsecase;
import com.example.closestv2.models.MemberAuthSigninPostRequest;
import com.example.closestv2.models.MemberAuthSignupPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberAuthController implements MemberAuthApi {
    private final MemberAuthUsecase memberAuthUsecase;

    @Override
    public ResponseEntity<Void> memberAuthSignupPost(MemberAuthSignupPostRequest request) {
        memberAuthUsecase.signUp(toServiceRequest(request));
        return ResponseEntity.ok().build();
    }
    private MemberAuthSignupPostServiceRequest toServiceRequest(MemberAuthSignupPostRequest request) {
        return new MemberAuthSignupPostServiceRequest(request.getEmail(), request.getPassword(), request.getConformPassword());
    }

    @Override
    public ResponseEntity<Void> memberAuthSigninPost(MemberAuthSigninPostRequest request) {
        memberAuthUsecase.signIn(toServiceRequest(request));
        return ResponseEntity.ok().build();
    }

    private MemberAuthSigninPostServiceRequest toServiceRequest(MemberAuthSigninPostRequest request) {
        return new MemberAuthSigninPostServiceRequest(request.getEmail(), request.getPassword());
    }
}
