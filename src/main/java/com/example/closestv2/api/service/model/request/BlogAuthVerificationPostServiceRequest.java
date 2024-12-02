package com.example.closestv2.api.service.model.request;


import lombok.Getter;

import java.net.URI;

@Getter
public class BlogAuthVerificationPostServiceRequest {
    private URI url;

    public BlogAuthVerificationPostServiceRequest(URI url) {
        this.url = url;
    }
}
