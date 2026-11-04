package com.surya.dbperformancelab.service;

import com.surya.dbperformancelab.dto.requestDto.CreateTransactionRequest;
import com.surya.dbperformancelab.dto.responseDto.PagedTransactionResponse;
import com.surya.dbperformancelab.dto.responseDto.TransactionResponse;
import com.surya.dbperformancelab.repository.TransactionRepository;
import com.surya.dbperformancelab.service.interfaces.TransactionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionServiceInterface {
    private final TransactionRepository transactionRepository;
    @Override
    public PagedTransactionResponse getTransactionsByAccountId(Long accountId, String status, String from, String to, Pageable pageable) {
        return null;
    }

    @Override
    public PagedTransactionResponse getTransactionsByStatus(String status, Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        return null;
    }
}
