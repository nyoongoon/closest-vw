package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class BlogEditService {

    private final BlogRepository blogRepository;

    public void editStatueMessage(URL blogUrl, String statusMessage) {

    }
}
