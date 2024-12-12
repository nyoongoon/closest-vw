package com.example.closestv2.api.service;

import com.example.closestv2.api.service.model.request.MyBlogStatusPatchServiceRequest;
import com.example.closestv2.api.usecases.MyBlogEditUsecase;
import com.example.closestv2.domain.member.MemberRepository;
import com.example.closestv2.domain.member.MemberRoot;
import com.example.closestv2.domain.member.event.StatusMessageEditEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyBlogEditService implements MyBlogEditUsecase {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * @see StatusMessageEditEvent
     */
    @Override
    @Transactional
    public void editMyBlogStatusMessage(long memberId, MyBlogStatusPatchServiceRequest serviceRequest) {
        MemberRoot memberRoot = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException(MEMBER_NOT_FOUND));
        StatusMessageEditEvent statusMessageEditEvent = memberRoot.withStatusMessage(serviceRequest.getMessage());//event 발행
        eventPublisher.publishEvent(statusMessageEditEvent);
    }
}
