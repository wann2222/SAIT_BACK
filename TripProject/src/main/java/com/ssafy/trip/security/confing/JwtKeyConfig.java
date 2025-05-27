package com.ssafy.trip.security.confing;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtKeyConfig {
	 @Value("${ssafy.jwt.secret}")
	    private String secret;

	    @Bean
	    public SecretKey jwtSecretKey() {
	        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	    }
}
