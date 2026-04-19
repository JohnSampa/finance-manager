package br.com.samp.financemanager.dto.response;

import java.util.UUID;

public record AccountResponse(
        UUID uuid,
        String bankName,
        Double balance
) {
}
