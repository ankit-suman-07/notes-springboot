package com.blogapp.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    // Always keep it in separate private file
    private static final String JWT_KEY = "demo-secret-key-for-project";

    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                //.withExpiresAt() // TODO: setup and expiry parameter
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodeJWT = JWT.decode(jwtString);
        var userId = Long.valueOf(decodeJWT.getSubject());
        return userId;
    }
}
