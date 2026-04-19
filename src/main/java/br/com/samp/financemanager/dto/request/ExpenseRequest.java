package br.com.samp.financemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record ExpenseRequest(
        @NotBlank(message = "Invalid amount")
        Double amount,

        @NotBlank(message = "Invalid date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank(message = "Invalid description")
        String description,

        @NotBlank(message = "Invalid categoriesIds")
        List<Long> categoryIds
) {
}
