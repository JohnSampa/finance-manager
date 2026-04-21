package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.enums.TransactionStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ExpenseResponse(
        UUID uuid,
        Double amount,
        LocalDate date,
        String description,
        TransactionStatus status,
        List<CategoryResponse> categories

) {
}
