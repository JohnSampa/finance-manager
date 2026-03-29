package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthLoginResponse;
import br.com.samp.financemanager.auth.dto.AuthRegisterResponse;
import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import br.com.samp.financemanager.dto.mapstruct.UserMapper;
import br.com.samp.financemanager.dto.request.UserRequest;
import br.com.samp.financemanager.dto.response.UserResponse;
import br.com.samp.financemanager.infrastructure.security.jwt.TokenService;
import br.com.samp.financemanager.model.User;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    public AuthLoginResponse authenticate(AuthenticationDTO authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authDto.email(),
                authDto.password()
        );

        var authentication = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return new AuthLoginResponse(token,"Bearer");

    }

    public AuthRegisterResponse register(UserRequest userRequest) {
        User user = userService.saveEntity(userRequest);

        var token = tokenService.generateToken(user);

        UserResponse userResponse = userMapper.toUserResponse(user);
        AuthLoginResponse authLoginResponse = new AuthLoginResponse(token,"Bearer");

        return new AuthRegisterResponse(userResponse,authLoginResponse);
    }
}
