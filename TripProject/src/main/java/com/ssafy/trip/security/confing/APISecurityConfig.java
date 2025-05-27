package com.ssafy.trip.security.confing;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.trip.security.filter.JWTAuthenticationFilter;
import com.ssafy.trip.security.filter.jwtVerificationFilter;
import com.ssafy.trip.security.service.CustomUserDetailService;


@Configuration
public class APISecurityConfig {
    @Bean

    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            CustomUserDetailService userDetailsService,
            JWTAuthenticationFilter authFilter,
            jwtVerificationFilter jwtVerifyFilter,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig)
            throws Exception {

        http.securityMatcher("/api/**") 
                .cors(cors -> cors.configurationSource(corsConfig))  
                .userDetailsService(userDetailsService)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/auth/**","/api/qna-comment/**","/api/qna/**", "/api/users/id/**" , "/api/question-mail" , "/api/hotplaces/**", "/api/attractions/**" ).permitAll()  
                                .requestMatchers(HttpMethod.POST, "/api/members").permitAll() 
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .anyRequest().authenticated())
                        		
                .addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
  
        return source;
    }
}
