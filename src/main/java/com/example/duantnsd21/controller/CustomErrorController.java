package com.example.duantnsd21.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Trả về trang lỗi tùy chỉnh
        return "error";
    }

    // Optional: Có thể override phương thức này nếu dùng Spring Boot 2.x
    public String getErrorPath() {
        return "/error";
    }
}
