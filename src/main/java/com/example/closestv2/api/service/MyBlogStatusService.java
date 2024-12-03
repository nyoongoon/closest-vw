package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogStatusUsecase;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import com.example.closestv2.domain.member.MyBlog;
import com.example.closestv2.domain.member.event.StatusMessageChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyBlogStatusService implements MyBlogStatusUsecase {
    private final MemberRepository memberRepository;

    @Override
    public void resetMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest) {
        MemberRoot memberRoot = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException(MEMBER_NOT_FOUND));
        memberRoot.withStatusMessage(serviceRequest.getMessage());
        MyBlog myBlog = memberRoot.getMyBlog();
        URL blogUrl = myBlog.getBlogUrl();
        String statusMessage = myBlog.getStatusMessage();
    }
}
