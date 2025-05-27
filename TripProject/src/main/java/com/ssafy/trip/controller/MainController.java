package com.ssafy.trip.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.service.AttractionService;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController extends HttpServlet implements ControllerHelper {
    private static final long serialVersionUID = 1L;
    private final AttractionService aService;
    private final BasicMemberService mService;
    
   
    @GetMapping("")
    private String index() {       
        return "index";
    }
    
    @GetMapping("index")
    public String Index() {
    	
    	return "index";
    }
    
    @GetMapping("PlanTest")
    public String PlanTest() {
    	return "plan-form";
    }
    
    @GetMapping("/search")
    private String SearchForm(Model model) {
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
        return "attraction/attraction-form";
    }
    
    @PostMapping("/attraction-view")
    private String Atrraction(@RequestParam String sido, @RequestParam String sigungu, @RequestParam String contentType, HttpSession session, @AuthenticationPrincipal CustomUserDetails details ) {
        try {

            List<Attraction> aList = aService.searchAttractions(sido, sigungu, contentType);
            System.out.println(sido+"" +sigungu+ " "+ contentType);
            

              int uid = details.getMember().getMno();
//            int uid  = ((Member) session.getAttribute("member")).getMno();
//            List<Plan> pList = aService.findTripPlanD(uid);
 //           session.setAttribute("pList", pList);
            session.setAttribute("aList", aList);
//            System.out.println(aList);
//            System.out.println(pList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "attraction/attraction-view";
    }
    
    @GetMapping("/plan-list")
    private String List(HttpSession session) {
        try {
            int uid  = ((Member) session.getAttribute("member")).getMno();

            List<Plan> pList = aService.findTripPlanD(uid);
            session.setAttribute("pList", pList);
            return "attraction/plan-view";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return "redirect/";
        }
    }
    
    @GetMapping("/make-plan-form")
    private String MakePlan() {
        return "attraction/make_plan";
    }
    
    @PostMapping("/make-plan")
    private String Plan(@RequestParam String name, @RequestParam String startDate, @RequestParam String endDate, HttpSession session) {
        
        try {

            int uid  = ((Member) session.getAttribute("member")).getMno();
            //Plan plan = new Plan(name, startDate, endDate);
            //aService.makePlan(uid, plan);
            String msg = "여행이 등록되었습니다";
            session.setAttribute("alertMsg", msg);
            return "redirect:/plan-list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return "redirect:/index";
        }
        
        
    }
    List<Attraction> aList, sList;
    int path[][];
    double lat, lon, weighted[][], dp[][];
    
    @GetMapping("/plan-detail")
    private String PlanDetail(HttpSession session, @RequestParam int pid, Model model) {
        try {
            Plan plan = aService.findTripPlanByPid(pid);
            aList = aService.planDetail(pid);
            getShortestPath(session);
            session.setAttribute("plan", plan);
            session.setAttribute("sList", sList);
            //System.out.println(aList);
            return "attraction/plan-detail";
            //forward(request, response, "/attraction/plan-detail.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return "redirect:/index";
            //redirect(request, response, "/");
        }
    }
    
    
    @PostMapping("/addToPlan")
    private String addToPlan(@RequestParam int pid, @RequestParam int ano, HttpSession session) {
        try {
            
            aService.addToPlan(pid, ano);
            String msg = "여행이 등록되었습니다";
            session.setAttribute("alertMsg", msg);
            return "redirect:/plan-detail";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return "redirect:/index";
        }
        
    }
   
    
    private void getShortestPath(HttpSession session){
        int len = aList.size();
        dp = new double[len+1][1<<(len+1)];
        path = new int[len+1][1<<(len+1)];
        weighted = new double[len+1][len+1];
        sList = new ArrayList<>();
        Member user = (Member) session.getAttribute("member");
        double Lat = user.getLat();
        double Lon = user.getLon();
        //double[] latlon = user.getLatLon();

        //lat = latlon[0]; lon=latlon[1];
        Attraction home = new Attraction(0, "집", Lat, Lon, user.getAddress(), "", "", 0);
        sList.add(home);
        
        for(int i=0;i<len;i++) {
            Attraction a = aList.get(i);
            // 위도, 경도 차이를 가중치로 사용
            double w = Math.abs(a.getLat()-lat)+Math.abs(a.getLon()-lon);
            weighted[0][i+1] = w;
            weighted[i+1][0] = w;
            for(int j=i+1;j<len;j++) {
                Attraction b = aList.get(j);
                 w = Math.abs(a.getLat()-b.getLat())+Math.abs(a.getLon()-b.getLon());
                 weighted[i+1][j+1] = w;
                 weighted[j+1][i+1] = w;
            }
        }
        int all=0;
        for(int i=1;i<len+1;i++) {
            all+= 1;
            all<<= 1;
        }
        getDistance(0,all);
        
        int mask = all;
        int point = 0;
        while(mask != 0) {
            int next = path[point][mask];
            System.out.println(point+" -> "+next);
            sList.add(aList.get(next-1));
            mask ^= (1<<next);
            point = next;
        }
        sList.add(home);
    }
    
        // 현재 point에서 mask에 있는 모든 점을 순회하고 다시 시작점으로 돌아오는 거리 계산
    private double getDistance(int point, int mask) {
        double minDist = Double.MAX_VALUE;
        if(mask==0) { // mask에 아무 도시도 없는 경우 바로 0번 도시로 직행
            return weighted[point][0];
        }
        // 이미 저장된 정보가 있는 경우 바로 반환
        if(dp[point][mask] != 0) return dp[point][mask];
        for(int i=1;i<aList.size()+1;i++) {
            if((mask & (1<<i))>0) { 
                if(weighted[point][i]==Integer.MAX_VALUE) continue; // 직접 다른 도시로 갈 수 없는 경우
                // 집합에 i번 원소가 포함된 경우 point->i번 도시, 나머지 도시를 순회하는 거리를 계산
                double dist = weighted[point][i] + getDistance(i, mask^(1<<i));
                if(dist<minDist && dist>0) {
                    minDist = dist;
                    path[point][mask] = i; // i번 도시로 향하는 기록 저장
                }
            }
        }
        dp[point][mask] = minDist;
        return minDist;
    }
}


