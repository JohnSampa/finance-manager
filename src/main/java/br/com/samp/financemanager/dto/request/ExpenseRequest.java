package br.com.samp.financemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ExpenseRequest(
        Double amount,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        String description,

        List<Long> categoryIds
) {
}
