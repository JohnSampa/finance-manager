package br.com.samp.financemanager.auth.dto;

public record AuthenticationDTO(
        String email,
        String password
) {
}
