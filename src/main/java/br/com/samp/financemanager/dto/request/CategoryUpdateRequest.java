package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @NotBlank(message = "Invalid name")
        String name,
        @NotBlank(message = "Invalid description")
        String description
) {
}
