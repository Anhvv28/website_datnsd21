package com.fpt.niceshoes.controller.chatgptcontroller;

import com.fpt.niceshoes.model.ChatRequest;
import com.fpt.niceshoes.service.chatgpt.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;


    @PostMapping
    public String getChatGPTResponse(@RequestBody ChatRequest chatRequest) {
        String userMessage = chatRequest.getUserMessage();
        return chatGPTService.getChatGPTResponse(userMessage);
    }
}
