package com.example.duantnsd21.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/oauth2/code/google")
    public String getGoogleUserInfo(@AuthenticationPrincipal OAuth2AuthenticationToken authentication, Model model) {
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");

        model.addAttribute("email", email);
        model.addAttribute("name", name);

        return "index"; // trả về trang home.html hoặc bất kỳ trang nào bạn muốn
    }
}
