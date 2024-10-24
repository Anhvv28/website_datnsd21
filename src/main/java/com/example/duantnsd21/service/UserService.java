package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(NguoiDungRepository nguoiDungRepository, PasswordEncoder passwordEncoder) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public NguoiDung saveNewUser(String email, String name, String rawPassword) {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTaiKhoan(email);
        nguoiDung.setMatKhau(passwordEncoder.encode(rawPassword));  // Mã hóa mật khẩu
        nguoiDung.setEmail(email);
        nguoiDung.setHoTen(name);

        return nguoiDungRepository.save(nguoiDung);
    }
}
