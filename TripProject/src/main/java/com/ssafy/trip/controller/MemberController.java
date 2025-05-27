package com.ssafy.trip.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.service.AttractionService;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*@WebServlet("/member")*/


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController extends HttpServlet implements ControllerHelper {
    private static final long serialVersionUID = 1L;
    private final BasicMemberService mService;
    private final AttractionService aService;
    private final  PasswordEncoder pe;
    static Map<String, List<String>> user = new HashMap<>();
    
    @PostConstruct
    public void init() {
        String tmphashpassword = String.valueOf(hash("12345"));
        user.putIfAbsent(tmphashpassword, new ArrayList<>());
        user.get(tmphashpassword).add("12345");
    }
    
    
    public static long hash(String s) {
        final int p = 53;
        final int m = (int) 1e9 + 9; // 큰 소수 (충돌 최소화)
        
        long hashVal = 0;
        long powP = 1;
        
        for (int i = 0; i < s.length(); i++) {
            hashVal = (hashVal + (s.charAt(i)) * powP) % m;
            powP = (powP * p) % m;
        }
        return hashVal;
    }
    
    @GetMapping("index")
    public String index() {
    	
    	return "index";
    }
    
    
    @GetMapping("/login-form")
    public String LoginForm() {
        return "member/member-login-form";
    }
    
    
    //@PostMapping("/login")
    private String login(Model model, @RequestParam String email, @RequestParam String password, HttpSession session) {
        try {
            String hashcode = String.valueOf(hash(password));
            
            Member member = mService.login(email, hashcode);
            if(member != null) {
                
                for(String chk : user.get(hashcode)) {
                    if(chk.equals(password)) { // 최종 로그인 처리
                        session.setAttribute("member", member);
                        return "redirect:/";
                    }
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "member/member-login-form";
    }
    
    
    //회원가입
    @GetMapping("/regist-member-form")
    private String RegistForm(Model model) {
    	List<String> sidoList = aService.getSidoList();
    	for(String s1 : sidoList) {
    		System.out.println(s1);
    	}
        // ② 시군구 맵
        Map<String,List<String>> sigunguMap = new HashMap<>();
        for (String s : sidoList) {
            sigunguMap.put(s, aService.getSigunguList(s));
        }
        model.addAttribute("sidoList",   sidoList);
        model.addAttribute("sigunguMap", sigunguMap);
        return "member/member-regist-form";
    }
    
    @PostMapping("regist-member")
    private String RegistMember(Model model, @RequestParam String email, @RequestParam String name, @RequestParam String password,
            @RequestParam String sido, @RequestParam String sigungu, @RequestParam String address,
            @RequestParam String x, @RequestParam String y, @RequestParam String lat, @RequestParam String lon,
            HttpSession session) {
        
        try {
        
           
            
            String Address = sido+" " + sigungu + " "+address;
            String hashpassword = pe.encode(password);
            Member m = new Member(name, email, hashpassword, Address);
            double X = Double.parseDouble(x);
            double Y = Double.parseDouble(y);
            double Lat = Double.parseDouble(lat);
            double Lon = Double.parseDouble(lon);
 
            m.setY(Y);
            m.setLat(Lat);
            m.setLon(Lon);
            

            
            int result = mService.registMember(m);
            if(result == 1) {
                String msg = "등록되었습니다. 로그인 후 사용해주세요";
                user.putIfAbsent(hashpassword, new ArrayList<>());
                user.get(hashpassword).add(password);
                session.setAttribute("alertMsg", msg);
                
                return "redirect:/";
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("alertMsg", e.getMessage());
        }
        return "member/member-regist-form";
    }
    
    
    @GetMapping("findPassword-member-form")
    private String FindPasswordForm() {
        return "member/findPassword-form";
    }
    
    @PostMapping("findPassword")
    private String FindPassword(@RequestParam String email, HttpSession session) {
        try {
            String password = pe.encode("12345");
            System.out.println("findPassword : "+email);
            int result = mService.findPassword(email, password);
            
            
            if(result == 1) { // 임시 비밀번호 업데이트
                
                session.setAttribute("alertMsg", "회원님 임시 비밀번호는 12345 입니다. 임시 비밀번호로 로그인 후 비밀번호를 새로 초기화 해주세요.");
                return "redirect:/";
            }
            
            
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("alertMsg", e.getMessage());
            // END
        }
        
        session.setAttribute("alertMsg", "회원 정보가 존재하지 않습니다. 다시 입력해주세요");
        return "member/findPassword-form";
        
    }
    
    //@GetMapping("/logout-form")
    private String LogoutForm(HttpSession session) {
        
        session.removeAttribute("member");
        session.setAttribute("alertMsg", "로그아웃 성공");
        return "redirect:/";
    }
    
    @GetMapping("/detail")
    private String Detail() {
        return "member/member-detail";
    }
    
    @GetMapping("update-form")
    private String UpdateForm(){
        return "member/member-update-form";
    }
    
    @PostMapping("update-member")
    private String Update(@RequestParam String name, @RequestParam String password, HttpSession session, @AuthenticationPrincipal CustomUserDetails details) {
        
        try {
            String email = details.getUsername();
        
            String changePassword = pe.encode(password);
            
            Member member = new Member();
            member.setMno(details.getMember().getMno());
            member.setName(name);
            member.setEmail(email);
            member.setPassword(changePassword);
            mService.modifyMember(member);
            // 성공했으면 세션 scope에 멤버 새로 저장
            
            
            
            user.putIfAbsent(changePassword, new ArrayList<>());
            user.get(changePassword).add(password);
            
            session.setAttribute("member", member);
            String msg = "회원정보 수정 성공";
            session.setAttribute("alertMsg", msg);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return "member/member-update-form";
        }
        
    }
    
    @GetMapping("delete")
    private String Delete(HttpSession session, @AuthenticationPrincipal CustomUserDetails details) {
        
        try {
            String email = details.getUsername();
            mService.deleteMember(email);
            session.removeAttribute("member");
            String msg = "회원탈퇴 완료";
            session.setAttribute("alertMsg", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/";
    }
    
    @GetMapping("member-list")
    private String ListForm(HttpSession session) {
    	
    	List<Member> list = mService.getMemberList();
    	session.setAttribute("memberList", list);
    	
        return "member/member-list";
    }

    
}