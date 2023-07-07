package com.ReservationServer1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


  @Value("${jwt.secret}")
  private String secretKey;


  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable().cors().and()
        .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeHttpRequests()
        // No Security
        .requestMatchers("/member/login", "/member", "/member/auth/pwd", "/swagger-ui/**", "/v3/api-docs/**")
        .permitAll()
        // member
        .requestMatchers(HttpMethod.PUT, "/member/info").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/member").hasAnyAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/member/pwd").hasAuthority("ROLE_PWD")
        // Store
        .requestMatchers(HttpMethod.POST, "/store").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/store").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/store/list").hasAuthority("ROLE_USER")
        // Store Info (Day Off)
        .requestMatchers(HttpMethod.POST, "/info/day").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.GET, "/info/day").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/info/day").hasAuthority("ROLE_OWNER")
        // Store Info (Time Info)
        .requestMatchers(HttpMethod.POST, "/info/time").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.GET, "/info/time").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/info/time").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/info/time").hasAuthority("ROLE_OWNER")
        // Store Table Info (Table Info)
        .requestMatchers(HttpMethod.POST, "/info/table").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.PUT, "/info/table").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/info/table").hasAuthority("ROLE_OWNER")
        // Store Foods Info (Foods Info)
        .requestMatchers(HttpMethod.POST, "/info/foods").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.GET, "/info/foods").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/info/foods").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/info/foods").hasAuthority("ROLE_OWNER")
        // Store Reservation And Order (Reservation Order Info)
        // Store Reservation
        .requestMatchers(HttpMethod.POST, "/por/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/por/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/por/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/por/reservation").hasAuthority("ROLE_USER")
        // Store Order
        .requestMatchers(HttpMethod.POST, "/por/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/por/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/por/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/por/order").hasAuthority("ROLE_USER")
        // Store Payment
        .requestMatchers(HttpMethod.POST, "/por/pay").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/por/pay").hasAuthority("ROLE_OWNER")
        // Store Payment Comment
        .requestMatchers(HttpMethod.POST, "/por/pay/comment").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/por/pay/comment").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/por/pay/comment").hasAuthority("ROLE_USER")
        // Store Payment Big Comment
        .requestMatchers(HttpMethod.POST, "/por/pay/bigcomment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.PUT, "/por/pay/bigomment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/por/pay/bigcomment").hasAuthority("ROLE_OWNER")
        // Store Board
        .requestMatchers(HttpMethod.POST, "/board").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/board").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/board").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/board").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/board/user").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.POST, "/board/comment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.PUT, "/board/comment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/board/comment").hasAuthority("ROLE_OWNER")

        .and()
        .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
