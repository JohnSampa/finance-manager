package br.com.samp.financemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ExpenseRequest(
        Double amount,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,

        String description
) {
}
