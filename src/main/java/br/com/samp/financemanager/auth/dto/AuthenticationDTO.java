package br.com.samp.financemanager.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 16,message = "Password must be at least 8 characters long")
        String password
) {
}
