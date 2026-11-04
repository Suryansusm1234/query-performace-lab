package com.surya.dbperformancelab.controller;

import com.surya.dbperformancelab.dto.responseDto.AccountSummaryResponse;
import com.surya.dbperformancelab.dto.responseDto.DailyTransactionSummary;
import com.surya.dbperformancelab.service.interfaces.AnalyticsServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsServiceInterface analyticsServiceInterface;

    @GetMapping("/transactions/daily")
    public ResponseEntity<List<DailyTransactionSummary>> getDailyAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(analyticsServiceInterface.getDailyTransactionAnalytics(startDate, endDate));
    }

    @GetMapping("/accounts/{id}/summary")
    public ResponseEntity<AccountSummaryResponse> getAccountSummary(@PathVariable Long id) {
        return ResponseEntity.ok(analyticsServiceInterface.getAccountSummary(id));
    }
}
