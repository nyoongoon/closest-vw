package com.example.closestv2.domain.member.event;

import java.net.URL;

public record StatusMessageEditEvent(
        URL blogUrl,
        String statusMessage
) {
}
