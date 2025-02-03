package br.com.sgm.inventory_management_system.util;

import br.com.sgm.inventory_management_system.exceptions.JwtKeyMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    private SecretKey secretKey;

    // Inicializa a chave de assinatura
    @PostConstruct
    public void init() {
        if (secret != null && secret.length() >= 32) {
            secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        } else {
            throw new JwtKeyMissingException();
        }
    }

    // Gera o token JWT
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Inclui o papel correto
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    // Extrai as claims do token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
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