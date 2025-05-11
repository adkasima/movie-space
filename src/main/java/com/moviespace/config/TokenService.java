package com.moviespace.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moviespace.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenService {

//    @Value("${flix.security.secret}")
//    private String secret;

    Algorithm algorithm = Algorithm.HMAC256("secret-key");

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API Moviespace")
                .sign(algorithm);
    }
}
