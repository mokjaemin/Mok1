package com.ReservationServer1.config;

import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ReservationServer1.utils.JWTutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authorization.split(" ")[1];
		if (JWTutil.isExpired(token, secretKey)) {
			filterChain.doFilter(request, response);
			return;
		}

		String userId = JWTutil.getUserId(token, secretKey);
		String userRole = JWTutil.getUserRole(token, secretKey);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, "",
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole)));

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
