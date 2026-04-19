package br.com.samp.financemanager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequest(
        @NotBlank(message = "Invalid name")
        String name,
        @Email(message = "Invalid email address")
        String email,
        @CPF(message = "Invalid CPF format")
        String cpf,
        @NotBlank(message = "Invalid zipcode")
        String zipcode,
        @NotBlank(message = "Invalid addressNumber")
        String addressNumber,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 16,message = "Password must be at least 8 characters long")
        String password
) {
}
