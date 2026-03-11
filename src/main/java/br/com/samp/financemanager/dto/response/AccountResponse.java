package br.com.samp.financemanager.dto.response;

public record AccountResponse(
        Long id,
        String bankName,
        Double balance
) {
}
