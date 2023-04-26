package com.ReservationServer1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import com.ReservationServer1.service.MemberService;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  
//  private final MemberService memberService;

  @Value("${jwt.secret}")
  private String secretKey;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.httpBasic().disable()
        .csrf().disable().cors().and().authorizeHttpRequests()
        .requestMatchers("/member/login", "/member", "/member/auth/pwd").permitAll()
        .requestMatchers(HttpMethod.POST, "/member/info").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.POST, "/member/pwd").hasAuthority("ROLE_PWD")
        .requestMatchers(HttpMethod.POST, "/store").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/member").hasAuthority("ROLE_USER")
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
