package com.example.closestv2.domain.member;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class MyBlog {
    private String url;
    private String statusMessage;
}
