package com.example.duantnsd21.controller;

import com.example.duantnsd21.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AppController {

    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        userService.addAuthenticatedUserInfoToModel(authentication, model);
        return "index";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/api/user-info")
    public ResponseEntity<Map<String, String>> getUserInfo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Map<String, String> userInfo = userService.extractUserInfo(authentication);
            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/admin")
    public String adminPage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            userService.addAuthenticatedUserInfoToModel(authentication, model);
            return "admin";
        }
        return "redirect:/login-form";
    }

    @GetMapping("/api/san-pham/nike-product")
    public String nike() {
        return "nike-product";
    }
}
