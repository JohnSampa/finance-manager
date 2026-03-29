package br.com.samp.financemanager.dto.response;

import br.com.samp.financemanager.model.Address;
import br.com.samp.financemanager.model.enums.UserStatus;
import jakarta.validation.Valid;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String cpf,
        UserStatus status,
        Address address,
        List<AccountResponse> accounts
) {
}
