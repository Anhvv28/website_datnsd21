package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.NhanVien;
import com.example.duantnsd21.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username);

        if (nguoiDung == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Collect authorities based on user's role
        Set<GrantedAuthority> authorities = new HashSet<>();

        NhanVien nhanVien = nguoiDung.getNhanVien();

        if (nhanVien != null && nhanVien.getVaiTro() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + nhanVien.getVaiTro().toUpperCase()));
        } else {
            throw new UsernameNotFoundException("User role not found");
        }


        return new org.springframework.security.core.userdetails.User(
                nguoiDung.getTaiKhoan(),
                nguoiDung.getMatKhau(),
                authorities
        );
    }
}

