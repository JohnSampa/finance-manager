package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        Double amount,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,
        String description,
        TransactionStatus status
) {
}
