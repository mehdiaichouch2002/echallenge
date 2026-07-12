package com.echallenge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(
            "Y2hhbmdlLW1lLWluLXByb2QtZWNoYWxsZW5nZS1zZWNyZXQta2V5LTI1Ng==", 60_000);

    @Test
    void generatedTokenCanBeParsedBack() {
        String token = jwtService.generateToken("user@test.com", "CANDIDATE", 42L);

        Claims claims = jwtService.parseToken(token);

        assertEquals("user@test.com", claims.getSubject());
        assertEquals("CANDIDATE", claims.get("role", String.class));
        assertEquals(42L, claims.get("userId", Long.class));
    }

    @Test
    void tamperedTokenIsRejected() {
        String token = jwtService.generateToken("user@test.com", "CANDIDATE", 42L);
        String tampered = token.substring(0, token.length() - 3) + "abc";

        assertThrows(JwtException.class, () -> jwtService.parseToken(tampered));
    }
}
