package com.ssafy.trip.restcontroller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.service.AttractionService;

import jakarta.websocket.server.PathParam;

import java.util.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attractions")
public class RestAttractionController {
    private final AttractionService Aservice;
    
    @GetMapping
    public ResponseEntity<?> getAll(){
        
        List<String> listAll = Aservice.getSidoList();
        
        // listAll은 비어있을 수가 없다.
        return ResponseEntity.ok(listAll);
        
    }
    
    @GetMapping("/condition-search")
    public ResponseEntity<?> conditionSearch(
    		@RequestParam(required = false) String sido_name,
    		@RequestParam(required = false) String no,
    		@RequestParam(required = false) String content_type_name){
    	try {
    		List<Attraction> result;
    		if(sido_name != null && content_type_name == null) {
    			result = Aservice.findBySido(sido_name);
    		
    		}else if(sido_name != null && content_type_name != null) {
    			result = Aservice.findBySidoContent(sido_name, content_type_name);
    		}else if(no != null && content_type_name == null) {
    			result = Aservice.findByGugun(Integer.parseInt(no));
    		}else if(no != null && content_type_name != null) {
    			result = Aservice.findByGugunContent(Integer.parseInt(no), content_type_name);
    		}else {
    			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    		}
    		return ResponseEntity.ok(result);
    	}catch(DataAccessException e) {
    		e.printStackTrace();
    		return ResponseEntity.internalServerError().body("조회 실패");
    	}
    }
    
    //http://localhost:8080/api/attractions/regions?sido=광주
    @GetMapping("/regions")
    public ResponseEntity<?> getRegion(@RequestParam String sido){
        
        List<String> listRegion = Aservice.getSigunguList(sido);
        return ResponseEntity.ok(listRegion);
        
    }
    
    
    
    //http://localhost:8080/api/attractions/search?sido=서울&sigungu=강남구&contentType=관광지
    @GetMapping("/search")
    public ResponseEntity<?> getSearch(@RequestParam String sido, @RequestParam String sigungu, @RequestParam String contentType){
        
        //System.out.println(sido + " " + sigungu + "  " + contType + "   test");
        try {
            List<Attraction> allSearch = Aservice.searchAttractions(sido, sigungu, contentType);
            return ResponseEntity.ok(allSearch);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }
    
    
    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> getSearch(@PathVariable String title){
        
        //System.out.println(sido + " " + sigungu + "  " + contType + "   test");
        try {
            List<Attraction> allSearch = Aservice.searchAttractionsByTitle(title);
            return ResponseEntity.ok(allSearch);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }
    
  //http://localhost:8080/api/attractions/search/sido/광주
    @GetMapping("/search/sido/{sido}")
    public ResponseEntity<?> searchBySido(@PathVariable String sido) {
        try {
            List<Attraction> results = Aservice.searchBySido(sido);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("시도별 검색 실패");
        }
    }
    
    @GetMapping("/search/ano/{ano}")
    public ResponseEntity<?> getAttractionByAno(@PathVariable int ano) {
        try {
            Attraction attraction = Aservice.getAttractionByAno(ano);
            if (attraction == null) {
                return ResponseEntity.status(404).body("존재하지 않는 관광지입니다.");
            }
            return ResponseEntity.ok(attraction);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }
    
//    {
//    	  "mno": 3,
//    	  "name": "여름 방학 제주 여행",
//    	  "start": "2024-07-20",
//    	  "end": "2024-07-25",
//    	  "details": [
//    	    {
//    	      "ano": 101,
//    	      "start": "2024-07-20 10:00:00",
//    	      "end": "2024-07-20 12:00:00"
//    	    },
//    	    {
//    	      "ano": 105,
//    	      "start": "2024-07-21 14:00:00",
//    	      "end": "2024-07-21 16:00:00"
//    	    },
//    	    {
//    	      "ano": 110,
//    	      "start": "2024-07-23 09:00:00",
//    	      "end": "2024-07-23 11:00:00"
//    	    }
//    	  ]
//    	}
    @PostMapping("/plans")
    public ResponseEntity<?> insertPlan(@RequestBody Plan plan){
   
        try {
            Aservice.insertPlan(plan);
            return ResponseEntity.ok("등록 성공");
            
        }
        
        catch (DataAccessException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("등록 실패");
        }
    }
    
    @GetMapping("/plans/{pid}") 
    public ResponseEntity<?> getPlan(@PathVariable int pid){
        try {
            Plan p = Aservice.getPlan(pid);
            return ResponseEntity.ok(p);
            
        }
        
        catch (DataAccessException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("실패");
        }
    }
    
    
    @PutMapping("/plans/{pid}")
    public ResponseEntity<?> updatePlan(@PathVariable int pid, @RequestBody Plan plan) {
        try {
            plan.setPid(pid);
            Aservice.updatePlan(plan);
            return ResponseEntity.ok("수정 성공");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("수정 실패");
        }
    }

    // 계획 삭제
    @DeleteMapping("/plans/{pid}")
    public ResponseEntity<?> deletePlan(@PathVariable int pid) {
        try {
            Aservice.deletePlan(pid);
            return ResponseEntity.ok("삭제 성공");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("삭제 실패");
        }
    }
    
    @GetMapping("/plans/userid/{userid}")
    public ResponseEntity<?> getPlansByUser(@PathVariable int userid) {
        try {
            List<Plan> plans = Aservice.getPlansByUser(userid);
            System.out.println(plans);
            return ResponseEntity.ok(plans);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }
    
    
    
    
}

