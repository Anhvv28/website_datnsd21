package com.example.duantnsd21.controller;


import com.example.duantnsd21.entity.KhachHang;
import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.repository.KhachHangRepository;
import com.example.duantnsd21.repository.NguoiDungRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class AppController {

    private final NguoiDungRepository nguoiDungRepository;
    private final KhachHangRepository khachHangRepository;

    public AppController(NguoiDungRepository nguoiDungRepository, KhachHangRepository khachHangRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            String email = principal.getAttribute("email");
            String name = principal.getAttribute("name");

            NguoiDung existingNguoiDung = nguoiDungRepository.findByEmail(email);
            if (existingNguoiDung == null) {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setTaiKhoan(email);
                nguoiDung.setMatKhau("");
                nguoiDung.setEmail(email);
                nguoiDung.setHoTen(name);
                nguoiDungRepository.save(nguoiDung);
            }

//             Lưu thông tin vào bảng khach_hang (nếu cần)
//            KhachHang existingKhachHang = khachHangRepository.findByEmail(email);
//            if (existingKhachHang == null) {
//                KhachHang khachHang = new KhachHang();
//                khachHang.setEmail(email);
//                khachHang.setSdt(principal.getAttribute("sdt")); // Nếu có sdt từ Google
//                khachHang.setDiaChi(""); // Set giá trị mặc định nếu không có
//                khachHang.setTongTien(BigDecimal.ZERO); // Giá trị mặc định
//                khachHang.setTrangThai(1); // Set trạng thái
//                khachHangRepository.save(khachHang);
//            }

            model.addAttribute("name", name);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        // logic
        return "login";
    }


}
