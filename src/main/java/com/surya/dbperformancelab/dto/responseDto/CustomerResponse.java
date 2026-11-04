package com.surya.dbperformancelab.dto.responseDto;

import lombok.Builder;

import java.time.Instant;
@Builder
public record CustomerResponse(
    Long id,
    String fullName,
    String email,
    Instant createdAt
) {}
