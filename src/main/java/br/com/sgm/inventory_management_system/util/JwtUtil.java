package br.com.sgm.inventory_management_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "minhaChaveSuperSecretaDeNoMinimo32Caracteres";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas
    // Gera o token JWT
    public String generateToken(String email,String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Inclui o papel correto
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extrai as claims do token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica se o token é válido
    public boolean isTokenValid(String token, String email) {
        String tokenEmail = extractClaims(token).getSubject();
        return email.equals(tokenEmail) && !isTokenExpired(token);
    }

    // Verifica se o token expirou
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}