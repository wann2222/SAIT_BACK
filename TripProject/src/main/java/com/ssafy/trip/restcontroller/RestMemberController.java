package com.ssafy.trip.restcontroller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestMemberController {
    private final BasicMemberService Bservice;
    private final PasswordEncoder pe;
    
    @GetMapping("/{email}")
    public ResponseEntity<?> getDetailMember(@PathVariable String email){
        
        try {
            Member member = Bservice.getMemberInfo(email);
            return ResponseEntity.ok(member);
            
        }catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }    
    }    
    
    
    @PostMapping
    public ResponseEntity<?> postMember(@RequestBody Member member){
        String pass = member.getPassword();
        String hashpass = pe.encode(pass);
        member.setPassword(hashpass);
        int res = Bservice.registMember(member);
        
        if(res == 1) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
        
    }
    
    
    @PutMapping
    public ResponseEntity<?> putMember(@RequestBody Member member){
        
        try {
            Bservice.modifyMember(member);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteMember(@PathVariable String email){
        
        try {
            Bservice.deleteMember(email);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        
        }
        
    }
    
    @GetMapping("/id/{mid}")
    public ResponseEntity<?> getMemberById(@PathVariable int mid) {
        Member member = Bservice.getMemberById(mid);
        if (member != null)
            return ResponseEntity.ok(member);
        else
            return ResponseEntity.notFound().build();
    }

    
    
}

