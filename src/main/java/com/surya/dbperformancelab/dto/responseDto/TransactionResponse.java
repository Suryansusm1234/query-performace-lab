package com.surya.dbperformancelab.dto.responseDto;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
    Long transactionId,
    Long senderAccountId,
    Long receiverAccountId,
    BigDecimal amount,
    String status,
    Instant createdAt
) {}
