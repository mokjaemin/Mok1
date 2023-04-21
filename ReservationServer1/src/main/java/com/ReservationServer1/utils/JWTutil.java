package com.ReservationServer1.utils;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTutil {
  
  
  public static String getUserId(String token, String secretKey) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody().get("userId", String.class);
  }
  
  
  public static String getUserRole(String token, String secretKey) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody().get("userRole", String.class);
  }
  
  
  public static boolean isExpired(String token, String secretKey) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        .getBody().getExpiration().before(new Date());
  }

  public static String createJWT(String userId, String role, String secretKey, long expireMs) {
    Claims claims = Jwts.claims(); // 일종의 MAP
    claims.put("userId", userId);
    claims.put("userRole", role);
    
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expireMs))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
