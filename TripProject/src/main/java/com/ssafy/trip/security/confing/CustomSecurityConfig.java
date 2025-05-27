package com.ssafy.trip.security.confing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig implements WebMvcConfigurer {
    @Bean
    RoleHierarchy roleHierachy() {
        return RoleHierarchyImpl.withDefaultRolePrefix() // role의 기본 prefix 설정: ROLE_
                .role("ADMIN").implies("USER").role("USER").implies("GUEST").build();
    }

    @Bean
    PasswordEncoder passEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    	
    @Bean
    SecurityFilterChain normalFilterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(authorize -> authorize
    			.anyRequest().permitAll()
    			);
    	
    	http.csrf(t -> t.disable());
    	http.formLogin(t -> t.loginPage("/member/member-login-form")
    				.loginProcessingUrl("/member/login")
    				.usernameParameter("email")
    				.successHandler((request, response, authentication) -> {
    					String rememberMe = request.getParameter("remember-me");
    					Cookie c = new Cookie("loginCookie",authentication.getName());
    					if(rememberMe != null) {
    						c.setMaxAge(60*60*24);
    					}else {
    						c.setMaxAge(0);
    					}
    					response.addCookie(c);
    					response.sendRedirect(request.getContextPath()+"/");   					
    				})
    				.failureUrl("/member/login-form?error")
    				.permitAll()
    			);
    	http.logout(t -> t
    			.deleteCookies("loginCookie")
    			.logoutUrl("/member/logout-form")
    			.invalidateHttpSession(true)
    			.logoutSuccessUrl("/")
    			
    			);
    		    				
    	http.rememberMe(t -> t.tokenValiditySeconds(60));
    	return http.build();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {

    	 registry.addMapping("/api/**") // 허용할 URL 패턴
    	 .allowedOrigins("http://localhost:5173", "http://localhost:5174") // Vue 개발 서버 주소
         .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
         .allowedHeaders("*")
         .allowCredentials(true);

    }
    

}