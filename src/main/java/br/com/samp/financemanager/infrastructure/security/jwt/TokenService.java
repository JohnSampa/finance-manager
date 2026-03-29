package br.com.samp.financemanager.infrastructure.security.jwt;

import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("S{application.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject(user.getId().toString())
                    .withIssuer("finance-manager")
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .withClaim("role", user.getRole().toString())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new BusinessException("Error generating token " + e.getMessage());
        }
    }
}
