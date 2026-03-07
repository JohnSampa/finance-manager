package br.com.samp.financemanager.dto;

public record AccountResponse(
        Long id,
        String bankName,
        Double balance
) {
}
