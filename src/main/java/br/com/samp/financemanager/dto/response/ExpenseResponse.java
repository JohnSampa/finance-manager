package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.enums.ExpenseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record ExpenseResponse(
        Long id,
        Double amount,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Instant date,
        String description,
        ExpenseStatus status
) {
}
