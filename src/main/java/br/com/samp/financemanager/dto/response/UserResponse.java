package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.Address;
import br.com.samp.financemanager.model.enums.UserStatus;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String name,
        String email,
        String cpf,
        UserStatus status,
        Address address,
        List<AccountResponse> accounts
) {
}
