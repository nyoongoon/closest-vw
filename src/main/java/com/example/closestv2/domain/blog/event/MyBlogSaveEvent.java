package com.example.closestv2.domain.blog.event;

import java.net.URL;

public record MyBlogSaveEvent(
        long memberId,
        URL blogUrl
) {
}
