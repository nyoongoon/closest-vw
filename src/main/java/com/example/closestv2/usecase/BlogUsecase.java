package com.example.closestv2.usecase;

import com.example.closestv2.models.AuthMessage;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface BlogUsecase {
    AuthMessage getBlogAuthMessageGet(URI url);
}
