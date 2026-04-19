package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
        @NotBlank(message = "Invalid name")
        String bankName,
        @NotBlank(message = "Invalid balance")
        Double balance
) {

}
