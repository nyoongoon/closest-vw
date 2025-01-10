package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MemberAuthSigninPostServiceRequest;
import com.example.closestv2.api.service.model.request.MemberAuthSignupPostServiceRequest;
import com.example.closestv2.api.usecases.MemberAuthUsecase;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.*;

@Service
@Validated
@RequiredArgsConstructor
public class MemberAuthService implements MemberAuthUsecase {
    private final MemberRepository memberRepository;

    @Override
    public void signUp(@Valid MemberAuthSignupPostServiceRequest serviceRequest) {
        String email = serviceRequest.getEmail();
        String password = serviceRequest.getPassword();
        String confirmPassword = serviceRequest.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(NOT_EQUAL_PASSWORDS);
        }

        boolean isDuplicated = memberRepository.existsByMemberInfoUserEmail(email);
        if (isDuplicated) {
            throw new IllegalArgumentException(DUPLICATED_EMAIL);
        }

        MemberRoot memberRoot = MemberRoot.create(serviceRequest.getEmail(), serviceRequest.getPassword()); //todo passwordEncoder
        memberRepository.save(memberRoot);
    }

    @Override
    public void signIn(MemberAuthSigninPostServiceRequest serviceRequest) {
        String email = serviceRequest.getEmail();
        String password = serviceRequest.getPassword();
        MemberRoot memberRoot = memberRepository.findByMemberInfoUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MEMBER));
        if(!password.equals(memberRoot.getPassword())){
            throw new IllegalArgumentException(INVALID_MEMBER);
        }
        //todo 로그인 완료
    }
}
