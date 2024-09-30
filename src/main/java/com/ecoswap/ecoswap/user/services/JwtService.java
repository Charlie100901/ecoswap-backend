package com.ecoswap.ecoswap.user.services;

import com.ecoswap.ecoswap.user.models.entities.User;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    String generateToken(User user, Map<String, Object> extraClaims);
    Key generateKey();
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    Claims extractAllClaims(String token);
}
