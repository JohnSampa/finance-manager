package br.com.samp.financemanager.dto;

public record UserRequest(
        String name,
        String username,
        String email,
        String cpf,
        String zipcode,
        String addressNumber,
        String password
) {
}
