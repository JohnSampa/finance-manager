package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AccountRequest(
        @NotBlank(message = "Invalid name")
        String bankName,
        @NotNull(message = "Balance is required")
        @Positive(message = "Balance cannot be negative")
        Double balance
) {

}
