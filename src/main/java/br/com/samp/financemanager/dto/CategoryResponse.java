package br.com.samp.financemanager.dto;

import br.com.samp.financemanager.model.enums.CategoryType;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        CategoryType type
) {
}
