package br.com.samp.financemanager.auth.dto;

public record AuthLoginResponse(
        String token,
        String type
) {
}
