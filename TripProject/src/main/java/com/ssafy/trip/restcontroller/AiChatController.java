package com.ssafy.trip.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/ai-chat")
@RequiredArgsConstructor
public class AiChatController {

    private final ChatClient simpleChatClient;  // Bean으로 등록된 ChatClient

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest req) {
        String prompt = req.getPrompt();
        // ChatClient 사용해서 AI 답변 얻기 (Simple 방식)
        String aiResponse = simpleChatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return ResponseEntity.ok(new ChatResponse(aiResponse));
    }

    // DTOs
    public static class ChatRequest {
        private String prompt;
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }
    public static class ChatResponse {
        private String answer;
        public ChatResponse(String answer) { this.answer = answer; }
        public String getAnswer() { return answer; }
    }
}
