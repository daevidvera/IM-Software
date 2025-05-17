package com.example.IV_backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {
    // Key encryption
    private final static SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final static long EXPIRATION_TIME = 86400000; // valid for a day

    // Store email in token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    public static String extractUsername(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY).build();

        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY).build();
        return parser.parseClaimsJws(token).getBody().getExpiration();
    }
}
