package br.com.samp.financemanager.dto;

import br.com.samp.financemanager.model.enums.CategoryType;

public record CategoryUpadateRequest(
        String name,
        String description
) {
}
