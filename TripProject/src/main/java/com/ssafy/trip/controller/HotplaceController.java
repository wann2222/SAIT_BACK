package com.ssafy.trip.controller;



import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Hotplace;
import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.model.service.HotplaceService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hotplace")
@RequiredArgsConstructor

public class HotplaceController {
    private final HotplaceService hService;
    private final BasicMemberService mService;
    
    
    @GetMapping("/test")
    private String hi(){
    	System.out.println("hi");
    	hService.toggleLike(2, 56645);
    	hService.toggleLike(2, 56646);
    	return "redirect:/";
    }
    @GetMapping("/test2")
    private String hi2(@AuthenticationPrincipal CustomUserDetails details, Model model) {
    	try {
			int mno = details.getMember().getMno();
			List<Attraction> list = hService.searchLike(mno);
			System.out.println(list);
			model.addAttribute("likes", list);
		} catch (DataAccessException e) {
			// TODO: handle exception
		}
    	
    	return "redirect:/";
    }
    @GetMapping("/search-hotplace")
    private String searchHotplace(HttpSession session){

        try {
            List<Hotplace> hList = hService.searchHotplace();
            session.setAttribute("hlist", hList);
            
            for(Hotplace h : hList) {
            	System.out.println(h.toString());
            }
                   
            return "hotplace/hotplace-view";
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
    
    @GetMapping("/register-form")
    private String goingRegister() {
        return "hotplace/hotplace-register-form";
    }
    
    
    @PostMapping("/regist-hotplace")
    private String registHotplace(HttpSession session, @RequestParam String contentType, @ModelAttribute Hotplace h) {
        int mid = ((Member)session.getAttribute("member")).getMno();
        h.setMid(mid);

        try {
            int content_type_id = hService.getContentTypeId(contentType);
            hService.regist(h, contentType, content_type_id);
            return "redirect:/hotplace/search-hotplace";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "redirect:/hotplace/regist-hotplace";
        }
    }
    
    
//    Hotplace h = new Hotplace(mid, title, date, image,lat,lon);
    
//    String title = req.getParameter("title");
//    String date = req.getParameter("date");
//    String contentType = req.getParameter("contentType");
//    double lat = Double.parseDouble(req.getParameter("lat"));
//    double  lon = Double.parseDouble(req.getParameter("lon"));
//    String image = req.getParameter("contentType");
}