package com.ssafy.trip.restcontroller;

import com.ssafy.trip.model.dto.QuestionMailRequestDto;
import com.ssafy.trip.model.service.QuestionMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/question-mail")
@RequiredArgsConstructor
public class RestQuestionMailController {

    private final QuestionMailService mailService;

    @PostMapping
    public ResponseEntity<?> sendQuestionMail(@RequestBody QuestionMailRequestDto dto) {
        try {
            mailService.sendQuestionMail(dto.getEmail(), dto.getTitle(), dto.getContent());
            return ResponseEntity.ok("메일 전송 성공!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("메일 전송 실패!");
        }
    }
}
