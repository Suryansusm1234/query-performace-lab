package com.surya.dbperformancelab.dto.requestDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateTransactionRequest(
    @NotNull(message = "Sender account ID is required")
    Long senderAccountId,

    @NotNull(message = "Receiver account ID is required")
    Long receiverAccountId,

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0001", message = "Amount must be positive")
    BigDecimal amount
) {}
