package com.surya.dbperformancelab.dto.responseDto;

import java.util.List;

public record PagedTransactionResponse(
    List<TransactionResponse> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
