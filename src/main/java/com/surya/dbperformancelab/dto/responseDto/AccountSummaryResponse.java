package com.surya.dbperformancelab.dto.responseDto;

import java.math.BigDecimal;

public record AccountSummaryResponse(
    Long accountId,
    BigDecimal totalIncoming,
    BigDecimal totalOutgoing,
    long transactionCount
) {}
