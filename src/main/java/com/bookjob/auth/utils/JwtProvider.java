package com.bookjob.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private static final long expireTime = 60 * 60 * 24 * 7;
    private static final String BEARER_PREFIX = "Bearer ";
    private final SecretKey key;

    public JwtProvider(@Value("${jwt.token.secretKey}") String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, String role) {
        return BEARER_PREFIX + Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expireTime)))
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    private String removeBearerPrefix(String token) {
        if (token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        throw new RuntimeException("Invalid JWT token");
    }

    public Jws<Claims> parseToken(String token) {
        try {
            String bearerRemovedToken = removeBearerPrefix(token);
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(bearerRemovedToken);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            String bearerRemovedToken = removeBearerPrefix(token);
            Jwts.parser().verifyWith(key).build().parseSignedClaims(bearerRemovedToken);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    // AccessToken 재발급
    public String reissueAccessToken(String refreshToken) {
        try {
            Jws<Claims> claims = parseToken(refreshToken);
            String loginId = claims.getPayload().getSubject();
            String role = claims.getPayload().get("role").toString();
            return createToken(loginId, role);
        } catch (Exception e) {
            throw new SecurityException("Invalid refresh token");
        }
    }
}