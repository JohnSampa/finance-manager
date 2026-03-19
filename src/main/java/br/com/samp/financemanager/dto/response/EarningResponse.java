package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.enums.TransactionStatus;

import java.time.LocalDate;
import java.util.List;

public record EarningResponse(
        Long id,

        Double amount,

        LocalDate date,

        String description,

        TransactionStatus status,

        List<CategoryResponse> categories
)   {
}
