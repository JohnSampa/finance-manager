package br.com.samp.financemanager.auth;

import br.com.samp.financemanager.auth.dto.AuthLoginResponse;
import br.com.samp.financemanager.auth.dto.AuthRegisterResponse;
import br.com.samp.financemanager.auth.dto.AuthenticationDTO;
import br.com.samp.financemanager.dto.mapstruct.UserMapper;
import br.com.samp.financemanager.dto.request.UserRequest;
import br.com.samp.financemanager.dto.response.UserResponse;
import br.com.samp.financemanager.exceptions.AccountDisableException;
import br.com.samp.financemanager.exceptions.AccountLockedException;
import br.com.samp.financemanager.exceptions.InvalidCredentialsException;
import br.com.samp.financemanager.infrastructure.security.jwt.TokenService;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.UserRepository;
import br.com.samp.financemanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    public AuthLoginResponse authenticate(AuthenticationDTO authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authDto.email(),
                authDto.password()
        );

        try {
            var authentication = authenticationManager.authenticate(usernamePassword);

            User user = (User) authentication.getPrincipal();

            user.setFailedLoginAttempts(0);

            user.setLockUntil(null);

            user = userRepository.save(user);

            var token = tokenService.generateToken(user);

            return new AuthLoginResponse(token, "Bearer");
        } catch (BadCredentialsException|UsernameNotFoundException exception) {

            userRepository.findUserByEmail(authDto.email()).ifPresent(user -> {

                user.registerFailedLoginAttempt();

                userRepository.save(user);

            });

            throw new InvalidCredentialsException();
        }catch (LockedException exception){
            User user = userRepository.findUserByEmail(authDto.email())
                    .orElse(null);

            long seconds = 0;

            if (user != null)
                seconds = user.getRemainingLockSeconds();

            throw new AccountLockedException(seconds);
        }catch (DisabledException exception){
            throw new AccountDisableException();
        }
    }

    public AuthRegisterResponse register(UserRequest userRequest) {
        User user = userService.saveEntity(userRequest);

        var token = tokenService.generateToken(user);

        UserResponse userResponse = userMapper.toUserResponse(user);
        AuthLoginResponse authLoginResponse = new AuthLoginResponse(token, "Bearer");

        return new AuthRegisterResponse(userResponse, authLoginResponse);
    }
}
