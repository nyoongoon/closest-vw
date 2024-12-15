package com.example.closestv2.api.service.model.request;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Getter
@Builder
public class SubscriptionsPostServiceRequest {
    private long memberId;
    private URL rssUrl;
}
