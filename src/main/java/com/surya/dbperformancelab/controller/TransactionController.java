package com.surya.dbperformancelab.controller;

import com.surya.dbperformancelab.dto.requestDto.CreateTransactionRequest;
import com.surya.dbperformancelab.dto.responseDto.PagedTransactionResponse;
import com.surya.dbperformancelab.dto.responseDto.TransactionResponse;
import com.surya.dbperformancelab.service.interfaces.TransactionServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceInterface transactionServiceInterface;

    // GET /api/accounts/{id}/transactions
    @GetMapping("/api/accounts/{id}/transactions")
    public ResponseEntity<PagedTransactionResponse> getAccountTransactions(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(direction, sortField));
        return ResponseEntity.ok(transactionServiceInterface.getTransactionsByAccountId(id, status, from, to, pageable));
    }

    // GET /api/transactions
    @GetMapping("/api/transactions")
    public ResponseEntity<PagedTransactionResponse> getTransactionsByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, Math.min(size, 100));
        return ResponseEntity.ok(transactionServiceInterface.getTransactionsByStatus(status, pageable));
    }

    // POST /api/transactions
    @PostMapping("/api/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionServiceInterface.createTransaction(request));
    }
}
