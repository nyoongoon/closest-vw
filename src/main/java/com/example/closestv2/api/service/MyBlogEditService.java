package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogEditUsecase;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyBlogEditService implements MyBlogEditUsecase {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void editMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest) {
        MemberRoot memberRoot = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException(MEMBER_NOT_FOUND));
        memberRoot.withStatusMessage(serviceRequest.getMessage()); //event 발행
    }
}
