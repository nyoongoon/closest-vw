package com.example.closestv2.util.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@RequiredArgsConstructor
public enum SpecificDate {
    EPOCH_TIME(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Asia/Seoul")));

    private final LocalDateTime localDateTime;
}
