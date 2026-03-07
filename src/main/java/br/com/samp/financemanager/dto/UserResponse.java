package br.com.samp.financemanager.dto;

import br.com.samp.financemanager.model.enums.UserStatus;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String username,
        String email,
        String cpf,
        UserStatus status,
        List<AccountResponse> accounts
) {
}
