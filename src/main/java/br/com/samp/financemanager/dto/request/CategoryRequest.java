package br.com.samp.financemanager.dto.request;

import br.com.samp.financemanager.model.enums.CategoryType;

public record CategoryRequest(
        String name,
        String description,
        CategoryType type
) {
}
