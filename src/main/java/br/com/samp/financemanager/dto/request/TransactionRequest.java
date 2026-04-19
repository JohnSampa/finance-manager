package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount cannot be negative")
        Double amount
) {
}
