package com.ssafy.trip.restcontroller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.model.dto.Qna;
import com.ssafy.trip.model.service.QnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class RestQnaController {
    private final QnaService qnaService;

    // QnA 전체 조회
    @GetMapping
    public ResponseEntity<?> searchAll() {
        try {
            List<Qna> list = qnaService.searchAll();
            return ResponseEntity.ok(list);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("조회 실패");
        }
    }

    // QnA 등록
    @PostMapping
    public ResponseEntity<?> regist(@RequestBody Qna qna) {
        try {
            qnaService.regist(qna);
            return ResponseEntity.ok("등록 성공");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("등록 실패");
        }
    }

    // QnA 상세 조회 (by no)
    @GetMapping("/{no}")
    public ResponseEntity<?> searchByNo(@PathVariable int no) {
        try {
            Qna qna = qnaService.searchByNo(no);
            if (qna != null)
                return ResponseEntity.ok(qna);
            else
                return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("상세 조회 실패");
        }
    }

    // QnA 수정
    @PutMapping("/{no}")
    public ResponseEntity<?> update(@PathVariable int no, @RequestBody Qna qna) {
        try {
            qna.setNo(no); // URL의 no와 body의 no를 일치시킴
            qnaService.update(qna);
            return ResponseEntity.ok("수정 성공");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("수정 실패");
        }
    }

    // QnA 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<?> delete(@PathVariable int no) {
        try {
            qnaService.delete(no);
            return ResponseEntity.ok("삭제 성공");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("삭제 실패");
        }
    }
}
