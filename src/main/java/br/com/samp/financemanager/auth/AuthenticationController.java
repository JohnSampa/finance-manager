package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthLoginResponse;
import br.com.samp.financemanager.auth.dto.AuthRegisterResponse;
import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import br.com.samp.financemanager.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@RequestBody AuthenticationDTO authDto) {
        var token = authService.authenticate(authDto);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponse> register(@RequestBody UserRequest user) {
        AuthRegisterResponse response = authService.register(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.userResponse().id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

}
