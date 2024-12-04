package com.example.closestv2.domain.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URL;

@Getter
@RequiredArgsConstructor
public class BlogAuthCode {
    private final long memberId;
    private final URL rssUrl;
    private final String authCode;
}
