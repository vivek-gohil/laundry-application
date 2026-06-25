package com.laundry.main.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.laundry.main.auth.config.JwtProperties;
import com.laundry.main.auth.entity.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(AppUser appUser) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(
                "roles",
                appUser.getRoles()
                        .stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toSet()));

        return generateToken(claims, appUser);
    }

    @Override
    public String generateToken(Map<String, Object> claims, AppUser appUser) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(appUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, AppUser appUser) {
        String username = extractUsername(token);
        return username.equals(appUser.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public Long getExpirationTime() {
        return jwtProperties.getExpiration() / 1000;
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        } catch (IllegalArgumentException ex) {
            keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
