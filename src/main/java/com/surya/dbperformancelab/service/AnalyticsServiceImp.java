package com.surya.dbperformancelab.service;

import com.surya.dbperformancelab.dto.responseDto.AccountSummaryResponse;
import com.surya.dbperformancelab.dto.responseDto.DailyTransactionSummary;
import com.surya.dbperformancelab.service.interfaces.AnalyticsServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class AnalyticsServiceImp implements AnalyticsServiceInterface {
    @Override
    public List<DailyTransactionSummary> getDailyTransactionAnalytics(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public AccountSummaryResponse getAccountSummary(Long accountId) {
        return null;
    }
}
