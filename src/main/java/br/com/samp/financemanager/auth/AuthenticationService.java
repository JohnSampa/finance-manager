package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import br.com.samp.financemanager.dto.request.UserRequest;
import br.com.samp.financemanager.dto.response.UserResponse;
import br.com.samp.financemanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public String authenticate(AuthenticationDTO authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authDto.email(),
                authDto.password()
        );

        var authentication = authenticationManager.authenticate(usernamePassword);

        return authentication.getPrincipal().toString();

    }

    public UserResponse register(UserRequest user) {
        return userService.save(user);
    }
}
