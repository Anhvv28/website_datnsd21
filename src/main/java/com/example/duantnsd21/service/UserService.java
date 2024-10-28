package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(NguoiDungRepository nguoiDungRepository, PasswordEncoder passwordEncoder) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addAuthenticatedUserInfoToModel(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            model.addAttribute("userName", oAuth2User.getAttribute("name"));
            model.addAttribute("userEmail", oAuth2User.getAttribute("email"));
            setRoleAttributes(oAuth2User.getAuthorities(), model);
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findByTaiKhoan(userDetails.getUsername());

            if (optionalNguoiDung.isPresent()) {
                NguoiDung nguoiDung = optionalNguoiDung.get();
                model.addAttribute("userName", nguoiDung.getHoTen() != null ? nguoiDung.getHoTen() : userDetails.getUsername());
                model.addAttribute("userEmail", nguoiDung.getEmail());
                if (nguoiDung.getNhanVien() != null) {
                    model.addAttribute("vaiTro", nguoiDung.getNhanVien().getVaiTro());
                }
            } else {
                model.addAttribute("userName", userDetails.getUsername());
                model.addAttribute("userEmail", "");
            }
            setRoleAttributes(userDetails.getAuthorities(), model);
        }
    }

    public Map<String, String> extractUserInfo(Authentication authentication) {
        Map<String, String> userInfo = new HashMap<>();
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            userInfo.put("hoTen", oAuth2User.getAttribute("name"));
            userInfo.put("email", oAuth2User.getAttribute("email"));
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userInfo.put("hoTen", userDetails.getUsername());

            Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findByTaiKhoan(userDetails.getUsername());
            if (optionalNguoiDung.isPresent()) {
                NguoiDung nguoiDung = optionalNguoiDung.get();
                userInfo.put("email", nguoiDung.getEmail());
                userInfo.put("hoTen", nguoiDung.getHoTen());
            }
        }
        return userInfo;
    }

    public NguoiDung saveNewUser(String email, String name, String rawPassword) {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTaiKhoan(email);
        nguoiDung.setMatKhau(passwordEncoder.encode(rawPassword));  // Mã hóa mật khẩu
        nguoiDung.setEmail(email);
        nguoiDung.setHoTen(name);

        return nguoiDungRepository.save(nguoiDung);
    }


    private void setRoleAttributes(Collection<? extends GrantedAuthority> authorities, Model model) {
        boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean isEmployee = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEmployee", isEmployee);
    }

}
