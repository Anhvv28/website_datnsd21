package com.example.duantnsd21.controller;


import com.example.duantnsd21.entity.KhachHang;
import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.NguoiDungDto;
import com.example.duantnsd21.repository.KhachHangRepository;
import com.example.duantnsd21.repository.NguoiDungRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AppController {

    private final NguoiDungRepository nguoiDungRepository;
    private final KhachHangRepository khachHangRepository;

    public AppController(NguoiDungRepository nguoiDungRepository, KhachHangRepository khachHangRepository ) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.khachHangRepository = khachHangRepository;

    }
    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                model.addAttribute("userName", oAuth2User.getAttribute("name"));
                model.addAttribute("userEmail", oAuth2User.getAttribute("email"));
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                model.addAttribute("userName", userDetails.getUsername());

                // Fetch additional user details from your database
                NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(userDetails.getUsername());
                if (nguoiDung != null) {
                    model.addAttribute("userEmail", nguoiDung.getEmail());
                    model.addAttribute("fullName", nguoiDung.getHoTen());
                }
            }
        }
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
            Object principal = authentication.getPrincipal();
            Map<String, String> userInfo = new HashMap<>();

            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                userInfo.put("hoTen", oAuth2User.getAttribute("name"));
                userInfo.put("email", oAuth2User.getAttribute("email"));
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                userInfo.put("hoTen", userDetails.getUsername());

                NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(userDetails.getUsername());
                if (nguoiDung != null) {
                    userInfo.put("email", nguoiDung.getEmail());
                    userInfo.put("hoTen", nguoiDung.getHoTen());
                }
            }
            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/admin")
    public String adminPage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User) {
                // OAuth2 authentication
                OAuth2User oAuth2User = (OAuth2User) principal;
                String userName = oAuth2User.getAttribute("name");
                String userEmail = oAuth2User.getAttribute("email");

                model.addAttribute("userName", userName);
                model.addAttribute("userEmail", userEmail);

                Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
                setRoleAttributes(authorities, model);

            } else if (principal instanceof UserDetails) {
                // Form-based authentication
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();

                NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username);
                if (nguoiDung != null) {
                    String hoTen = nguoiDung.getHoTen();
                    String userEmail = nguoiDung.getEmail();

                    String userName = (hoTen != null && !hoTen.isEmpty()) ? hoTen : username;

                    model.addAttribute("userName", userName);
                    model.addAttribute("userEmail", userEmail);
                } else {
                    model.addAttribute("userName", username);
                    model.addAttribute("userEmail", "");
                }

                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                setRoleAttributes(authorities, model);
            }
        } else {
            return "redirect:/login-form";
        }
        return "admin";
    }

    private void setRoleAttributes(Collection<? extends GrantedAuthority> authorities, Model model) {
        boolean isAdmin = false;
        boolean isEmployee = false;

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                isAdmin = true;
            } else if (role.equals("ROLE_EMPLOYEE")) {
                isEmployee = true;
            }
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEmployee", isEmployee);
    }
}