package com.ReservationServer1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {


  @Value("${jwt.secret}")
  private String secretKey;
  


  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable().csrf().disable().cors()
        .and()
        .authorizeHttpRequests()
        // No Security
        .requestMatchers("/member/login", "/member", "/member/auth/pwd", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
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
        .requestMatchers(HttpMethod.POST, "/rop/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/rop/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/rop/reservation").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/rop/reservation").hasAuthority("ROLE_USER")
        // Store Order
        .requestMatchers(HttpMethod.POST, "/rop/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/rop/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/rop/order").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/rop/order").hasAuthority("ROLE_USER")
        // Store Payment
        .requestMatchers(HttpMethod.POST, "/rop/pay").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/rop/pay").hasAuthority("ROLE_OWNER")
        // Store Payment Comment
        .requestMatchers(HttpMethod.POST, "/rop/pay/comment").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/rop/pay/comment").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/rop/pay/comment").hasAuthority("ROLE_USER")
        // Store Payment Big Comment
        .requestMatchers(HttpMethod.POST, "/rop/pay/bigcomment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.PUT, "/rop/pay/bigomment").hasAuthority("ROLE_OWNER")
        .requestMatchers(HttpMethod.DELETE, "/rop/pay/bigcomment").hasAuthority("ROLE_OWNER")

        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
