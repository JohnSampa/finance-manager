package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.enums.CategoryType;

import java.util.UUID;

public record CategoryResponse(
        UUID uuid,
        String name,
        String description,
        CategoryType type
) {
}
