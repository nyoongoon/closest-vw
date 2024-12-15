package com.example.closestv2.api.service.model.request;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Getter
public class SubscriptionsPostServiceRequest {
    private long memberId;
    private URL rssUrl;

    @Builder
    public SubscriptionsPostServiceRequest(long memberId, URL rssUrl) {
        this.memberId = memberId;
        this.rssUrl = rssUrl;
    }
}
