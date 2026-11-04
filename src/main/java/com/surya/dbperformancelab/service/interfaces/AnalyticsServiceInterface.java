package com.surya.dbperformancelab.service.interfaces;

import com.surya.dbperformancelab.dto.responseDto.AccountSummaryResponse;
import com.surya.dbperformancelab.dto.responseDto.DailyTransactionSummary;
import java.time.LocalDate;
import java.util.List;

public interface AnalyticsServiceInterface {
    List<DailyTransactionSummary> getDailyTransactionAnalytics(LocalDate startDate, LocalDate endDate);
    AccountSummaryResponse getAccountSummary(Long accountId);
}
