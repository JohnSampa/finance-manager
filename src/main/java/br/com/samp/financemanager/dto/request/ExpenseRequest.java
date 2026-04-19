package br.com.samp.financemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record ExpenseRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount cannot be negative")
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
