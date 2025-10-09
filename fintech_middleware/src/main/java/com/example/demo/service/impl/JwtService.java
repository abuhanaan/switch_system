package com.example.demo.service.impl;

import com.example.demo.config.TokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

  private final TokenConfig tokenConfig;

  public String generateToken(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + tokenConfig.getExpirationMs()))
        .signWith(SignatureAlgorithm.HS256, tokenConfig.getSecretKey().getBytes())
        .compact();
  }

  public String extractEmail(String token) {
    return getClaims(token).getSubject();
  }

  public boolean validateToken(String token, String email) {
    String tokenEmail = extractEmail(token);
    return (tokenEmail.equals(email) && !isTokenExpired(token));
  }

  private Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(tokenConfig.getSecretKey().getBytes())
        .parseClaimsJws(token)
        .getBody();
  }

  private boolean isTokenExpired(String token) {
    return getClaims(token).getExpiration().before(new Date());
  }
}
