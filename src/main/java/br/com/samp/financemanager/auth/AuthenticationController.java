package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping
    public ResponseEntity login(@RequestBody AuthenticationDTO authDto) {
        var token = authService.authenticate(authDto);

        return ResponseEntity.ok(token);
    }
}
