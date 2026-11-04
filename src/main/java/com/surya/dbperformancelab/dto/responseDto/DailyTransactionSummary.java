package com.surya.dbperformancelab.dto.responseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyTransactionSummary(
    LocalDate date,
    long totalTransactions,
    BigDecimal totalVolume,
    long successfulCount
) {}
