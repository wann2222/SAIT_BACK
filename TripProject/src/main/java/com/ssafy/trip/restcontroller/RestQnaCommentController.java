package com.ssafy.trip.restcontroller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.QnaComment;
import com.ssafy.trip.model.service.QnaCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/qna-comment")
@RequiredArgsConstructor
public class RestQnaCommentController {
	private final QnaCommentService qService;
	
	//댓글 전체 조회
	@GetMapping
	 public ResponseEntity<?> searchAll(){
		try {
			List<QnaComment> list = qService.searchAll();
			return ResponseEntity.ok(list);
		}catch(DataAccessException e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("조회 실패");
		}
	}
	
	//댓글 등록
	@PostMapping
	public ResponseEntity<?> regist(@RequestBody QnaComment qnacomment){
		try {
			qService.regist(qnacomment);
			return ResponseEntity.ok("등록 성공");
		}catch(DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("등록 실패");
		}
	}
	
	//게시글 별 댓글 조회
	@GetMapping("/{qna_id}")
	public ResponseEntity<?> searchByPostId(@PathVariable int qna_id){
		try {
			List<QnaComment> list = qService.searchByQnaId(qna_id);
			return ResponseEntity.ok(list);
		}catch(DataAccessException e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("게시글별 댓글 조회 실패");
		}
	}
	
	//댓글 삭제
	@DeleteMapping("/{cid}")
	public ResponseEntity<?> deleteBy(@PathVariable int cid){
		try {
			qService.delete(cid);
			return ResponseEntity.ok("삭제 성공");
		}catch(DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("삭제 실패");
		}
	}
	
}
