package com.ssafy.trip.restcontroller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Hotplace;
import com.ssafy.trip.model.dto.Likes;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.model.service.HotplaceService;
import com.ssafy.trip.model.service.PostService;

import lombok.RequiredArgsConstructor;
import java.util.*;

@RestController
@RequestMapping("/api/hotplaces")
@RequiredArgsConstructor
public class RestHotplaceController {
	
	private final HotplaceService hService;
	
	// 시도 내에서 조회 http://localhost:8080/api/hotplaces/sido?sido=서울&offset=2
	@GetMapping("/sido")
	public ResponseEntity<List<Attraction>> searchHotplaceBysido(@RequestParam String sido, @RequestParam int offset) {
		try {
		List<Attraction> hlist = hService.searchHotplaceBysido(sido, offset);
		return ResponseEntity.ok(hlist);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}		
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<Attraction>> searchHotplaceBysido(@RequestParam int no, @RequestParam int offset) {
		try {
		List<Attraction> hlist = hService.searchHotplaceBygugun(no, offset);
		return ResponseEntity.ok(hlist);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}		
	}
	
	
	// 시도 -> 컨텐츠 조회 http://localhost:8080/api/hotplaces/sido/content?sido=서울&content=관광지&offset=1
	@GetMapping("/sido/content")
	public ResponseEntity<List<Attraction>> searchHotplaceBycontetnt (@RequestParam String sido, @RequestParam String content, @RequestParam int offset) {
		
		try {
			List<Attraction> hlist = hService.searchHotplaceBycontetnt(sido, content,offset);
		return ResponseEntity.ok(hlist);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/likes/{uid}")
	public ResponseEntity<List<Attraction>> searchLike (@PathVariable int uid) {
			
		try {
			List<Attraction> hlist = hService.searchLike(uid);
		return ResponseEntity.ok(hlist);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
//	POST /likes
// http://localhost:8080/api/hotplaces/likes
//		{
//		  "uid": 4,
//		  "ano": 56645
//		}
	
	@PostMapping("/likes/status")
	public ResponseEntity<Map<String, Object>> exsistList(@RequestBody Likes like) {
	    try {
			Boolean result = hService.exsistList(like.getUid(), like.getAno());
				
		    Map<String, Object> response = new HashMap<>();
		    response.put("status", result); // true or false
		    response.put("ano", like.getAno());
		    return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/likes")
	public ResponseEntity<Map<String, Object>> toggleLike(@RequestBody Likes like) {
 
	    try {
			String result = hService.toggleLike(like.getUid(), like.getAno());
				
		    Map<String, Object> response = new HashMap<>();
		    int updatedLikes = hService.getLikeCount(like.getAno());
		    response.put("status", result); // liked or unliked
		    response.put("ano", like.getAno());
		    response.put("updatedLikes", updatedLikes);
		    return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/likesList/{uid}")
	public ResponseEntity<List<Integer>> searchLikedAnoListMno (@PathVariable int uid) {
			
		try {
			List<Integer> hlist = hService.searchAnoList(uid);
		return ResponseEntity.ok(hlist);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	
	
	
}


