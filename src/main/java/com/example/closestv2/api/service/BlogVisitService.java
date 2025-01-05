package com.example.closestv2.api.service;

import com.example.closestv2.domain.blog.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogVisitService {
    private final BlogRepository blogRepository;

    public void visitBlog(){

    }

    public void visitPost(){

    }
}
