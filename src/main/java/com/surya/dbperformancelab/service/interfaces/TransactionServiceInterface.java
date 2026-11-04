package com.surya.dbperformancelab.service.interfaces;

import com.surya.dbperformancelab.dto.requestDto.CreateTransactionRequest;
import com.surya.dbperformancelab.dto.responseDto.PagedTransactionResponse;
import com.surya.dbperformancelab.dto.responseDto.TransactionResponse;
import org.springframework.data.domain.Pageable;

public interface TransactionServiceInterface {
    PagedTransactionResponse getTransactionsByAccountId(
        Long accountId,
        String status,
        String from,
        String to,
        Pageable pageable
    );

    PagedTransactionResponse getTransactionsByStatus(String status, Pageable pageable);

    TransactionResponse createTransaction(CreateTransactionRequest request);
}
