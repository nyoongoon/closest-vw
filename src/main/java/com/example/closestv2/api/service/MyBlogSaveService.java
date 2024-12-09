package com.example.closestv2.api.service;

import com.example.closestv2.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class MyBlogSaveService {
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMyBlog(long memberId, URL blogUrl){

    }
}
