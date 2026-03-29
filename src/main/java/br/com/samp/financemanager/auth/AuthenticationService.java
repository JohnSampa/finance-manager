package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticate(AuthenticationDTO authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authDto.email(),
                authDto.password()
        );

        var authentication = authenticationManager.authenticate(usernamePassword);


        return authentication.getPrincipal().toString();

    }
}
