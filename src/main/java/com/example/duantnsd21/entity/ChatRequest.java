package com.example.duantnsd21.entity;


public class ChatRequest {
    private String message;

    public ChatRequest() {
    }

    public ChatRequest(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

