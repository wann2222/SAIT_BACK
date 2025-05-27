package com.ssafy.trip.restcontroller;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.service.BasicMemberService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RestJwtController {
	
	private final BasicMemberService memberService;
	private final SecretKey jwtSecretKey;

    
    @Value("${ssafy.jwt.access-expmin}")
    private long accessExpMin;

    @Value("${ssafy.jwt.refresh-expmin}")
    private long refreshExpMin;
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
    	
    	var parser = Jwts.parser().verifyWith(jwtSecretKey).build();
    	
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }

        Map<String, Object> claims = parser.parseSignedClaims(refreshToken).getPayload();
        String email = (String) claims.get("email"); 

        if (email == null) {
            throw new JwtException("Invalid refresh token: email claim missing");
        }

        Member member = memberService.getMemberInfo(email);

        if (member == null || member.getRefresh() == null || !member.getRefresh().equals(refreshToken)) {
           
        	
           
        	return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "FAIL", "error", "Invalid refresh token"));
        }


        Map<String, Object> accessclaims = Map.of("email", member.getEmail(), "name", member.getName(), "role", member.getRole(), "mno", member.getMno());        
        String newAccessToken = Jwts.builder().subject("accessToken").claims(accessclaims).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * accessExpMin)).signWith(jwtSecretKey).compact();
        
        Map<String, Object> refreshclaims = Map.of("email", member.getEmail());
        String newRefreshToken = Jwts.builder().subject("refreshToken").claims(refreshclaims).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * refreshExpMin)).signWith(jwtSecretKey).compact();

  
        member.setRefresh(newRefreshToken); 
        memberService.modifyMember(member); 

        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "SUCCESS", "accessToken", newAccessToken, "refreshToken", newRefreshToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Refresh-Token") String refreshToken) {
    	
    	  var parser = Jwts.parser().verifyWith(jwtSecretKey).build();
    		
    	  if (refreshToken == null || refreshToken.isEmpty()) {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
          }
    	  
    	  Map<String, Object> claims = parser.parseSignedClaims(refreshToken).getPayload();
          String email = (String) claims.get("email"); 

          if (email == null) {
              throw new JwtException("Invalid refresh token: email claim missing");
          }
          
          Member member = memberService.getMemberInfo(email);
          member.setRefresh(null);
          memberService.modifyMember(member);
          
          return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "SUCCESS", "accessToken", "", "refreshToken", ""));

    }
}
