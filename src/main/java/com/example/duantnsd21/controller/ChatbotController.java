package com.example.duantnsd21.controller;

import com.example.duantnsd21.entity.ChatRequest;
import com.example.duantnsd21.entity.ChatResponse;
import com.example.duantnsd21.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatbotController {

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chatbot")
    public ResponseEntity<?> chatWithGPT(@RequestBody ChatRequest chatRequest) {
        String userMessage = chatRequest.getMessage();
        String botReply = chatGPTService.getChatGPTResponse(userMessage);
        return ResponseEntity.ok(new ChatResponse(botReply));
    }
}
