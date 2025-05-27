package com.ssafy.trip.restcontroller;

import com.ssafy.trip.model.dto.Page;
import com.ssafy.trip.model.dto.Post;
import com.ssafy.trip.model.dto.SearchCondition;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.model.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class RestBoardController {
    private final PostService pService;
    private final BasicMemberService mService;

    // GET /api/posts/{no} - 게시글 상세 조회
    @GetMapping("/{no}")
    public ResponseEntity<?> getPost(@PathVariable int no) {
        Post post = pService.getPost(no);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/posts - 전체 게시글 목록 조회 (기본 페이징)
    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam(defaultValue = "1") int page) {
        try {
            Page<Post> postPage = pService.search(new SearchCondition("", "", page));
            return ResponseEntity.ok(postPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }
    
    // GET /api/posts/search?key=title&word=여행&page=1 - 키워드 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam String key, @RequestParam String word,
                                         @RequestParam(defaultValue = "1") int page) {
        try {
            Map<String, String> keyMap = Map.of("1", "name", "2", "title", "3", "content");
            System.out.println(keyMap);
            if (key != null) {
                key = keyMap.getOrDefault(key, "");
            }
            Page<Post> postPage = pService.search(new SearchCondition(key, word, page));
            System.out.println(key + " " + word);
            return ResponseEntity.ok(postPage);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("검색 실패");
        }
    }

    // POST /api/posts - 게시글 등록
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            post.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pService.regist(post);
            return ResponseEntity.ok("등록 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("등록 실패");
        }
    }

    // PUT /api/posts/{no} - 게시글 수정
    @PutMapping("/{no}")
    public ResponseEntity<?> updatePost(@PathVariable int no, @RequestBody Post post) {
        try {
            post.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
            pService.update(post, no);
       
            return ResponseEntity.ok("수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("수정 실패");
        }
    }

    // DELETE /api/posts/{no} - 게시글 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<?> deletePost(@PathVariable int no) {
        try {
            pService.delete(no);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("삭제 실패");
        }
    }
}