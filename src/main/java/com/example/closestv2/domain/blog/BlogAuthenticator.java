package com.example.closestv2.domain.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * Blog 도메인 서비스 구현체
 */
@Component
@RequiredArgsConstructor
public class BlogAuthenticator {
    public BlogAuthCode createAuthCode(long memberId, URL rssUrl) {

        return null;
    }

    public boolean authenticate(long memberId) {
        return false;
    }
}
