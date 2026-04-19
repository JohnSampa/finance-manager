package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TransactionRequest(
        @NotBlank(message = "Invalid amount")
        Double amount
) {
}
