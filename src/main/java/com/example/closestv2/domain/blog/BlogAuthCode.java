package com.example.closestv2.domain.blog;

import java.net.URL;

public record BlogAuthCode(
        long memberId,
        URL rssUrl,
        String authMessage
) {
}
