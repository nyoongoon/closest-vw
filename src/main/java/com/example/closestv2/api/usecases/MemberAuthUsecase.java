package com.example.closestv2.api.usecases;

import com.example.closestv2.api.service.model.request.MemberAuthSigninPostServiceRequest;
import com.example.closestv2.api.service.model.request.MemberAuthSignupPostServiceRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberAuthUsecase {
    void signUp(MemberAuthSignupPostServiceRequest serviceRequest);
    void signIn(MemberAuthSigninPostServiceRequest serviceRequest);
}
