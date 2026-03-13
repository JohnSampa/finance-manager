package br.com.samp.financemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record ExpenseRequest(
        Double amount,

        @JsonFormat(pattern = "dd/MM/yyyy")
        Instant date,

        String description
) {
}
