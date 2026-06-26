package com.laundry.main.auth.jwt;

import com.laundry.main.auth.entity.AppUser;
import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

  String generateToken(AppUser user);

  String generateToken(Map<String, Object> claims, AppUser user);

  String extractUsername(String token);

  boolean isTokenValid(String token, AppUser user);

  Long getExpirationTime();

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
