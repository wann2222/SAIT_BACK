package com.ssafy.trip.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final SecretKey jwtSecretKey;
	private final BasicMemberService memberService;
	
    @Value("${ssafy.jwt.access-expmin}")
    private long accessExpMin;

    @Value("${ssafy.jwt.refresh-expmin}")
    private long refreshExpMin;
	
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, BasicMemberService memberService, SecretKey jwtSecretKey) {
		super(authenticationManager);
		this.jwtSecretKey = jwtSecretKey;
		this.memberService = memberService;
        this.setFilterProcessesUrl("/api/member/login"); 
        this.setUsernameParameter("email"); 
        this.setPasswordParameter("password"); 
    }
    
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {

        Member member =  ((CustomUserDetails)authentication.getPrincipal()).getMember();
        if (member.getRole() == null) {
            member.setRole("USER");
        }
        
        Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * accessExpMin);
        
        Map<String, Object> accessclaims = Map.of("email", member.getEmail(), "name", member.getName(), "role", member.getRole(), "mno", member.getMno());
        String accessToken = Jwts.builder().subject("accessToken").claims(accessclaims).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * accessExpMin)).signWith(jwtSecretKey).compact();
        

        Map<String, Object> refreshclaims = Map.of("email", member.getEmail());
        String refreshToken = Jwts.builder().subject("refreshToken").claims(refreshclaims).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * refreshExpMin)).signWith(jwtSecretKey).compact();

        member.setRefresh(refreshToken);
        memberService.modifyMember(member);
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("data", Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        
        response.setContentType("application/json;charset=UTF-8");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(result);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
      
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
    
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {
       
        Map<String, Object> result = new HashMap<>();
        result.put("status", "FAIL");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(result);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {     
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
    }
    
}
