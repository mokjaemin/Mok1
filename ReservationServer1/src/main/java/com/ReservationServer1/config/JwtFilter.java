package com.ReservationServer1.config;

import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  
  
  private final MemberService memberService;
  private final String secretKey;
  
  

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    
    if (authorization == null) {
      System.out.println("잘못된 토큰");
      filterChain.doFilter(request, response);
      return;
    }
    
    
    String token = authorization.split(" ")[1];

    
    // Expired Check
    if (JWTutil.isExpired(token, secretKey)) {
      System.out.println("만료된 토큰");
      filterChain.doFilter(request, response);
      return;
    };
    
    
    
    // Token에서 꺼냄
    String userId = JWTutil.getUserId(token, secretKey);
    String userRole = JWTutil.getUserRole(token, secretKey);
    
    // userId, credentials, roll
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userId, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole)));
    

    // Detail Build
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }


}
