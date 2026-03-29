package br.com.samp.financemanager.infrastructure.security.jwt;

import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.UnauthorizedException;
import br.com.samp.financemanager.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${application.security.token.secret}")
    private String secret;

    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user) {
        try {
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

    public DecodedJWT validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("finance-manager")
                    .build()
                    .verify(token);
        }catch (JWTVerificationException e){
            throw new UnauthorizedException("Invalid or expired token");

        }
    }
}
