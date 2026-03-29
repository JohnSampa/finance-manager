package br.com.samp.financemanager.auth.dto;

import br.com.samp.financemanager.dto.response.UserResponse;

public record AuthRegisterResponse(
        UserResponse userResponse,
        AuthLoginResponse token
) {

}
