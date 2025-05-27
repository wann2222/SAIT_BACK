package com.ssafy.trip.security.filter;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.trip.security.service.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class jwtVerificationFilter extends OncePerRequestFilter{
	private final SecretKey jwtSecretKey;
	private final CustomUserDetailService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = request.getHeader("Authorization");
			String jwt;
			
			if(token != null && token.startsWith("Bearer ")) {
				jwt = token.substring(7);
			}else {
				filterChain.doFilter(request, response);
                return;
			}
			
			var parser = Jwts.parser().verifyWith(jwtSecretKey).build();
			
			Claims claims = parser.parseSignedClaims(jwt).getPayload();
			UserDetails userDetails = userDetailService.loadUserByUsername(claims.get("email").toString());
			var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
		
	}
	

}
