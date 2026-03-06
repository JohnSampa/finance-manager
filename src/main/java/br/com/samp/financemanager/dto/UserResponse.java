package br.com.samp.financemanager.dto;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String username,
        String email,
        String cpf,
        List<AccountDTO> accounts
) {
}
