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
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(usernameOrEmail)
                .orElseGet(() -> nguoiDungRepository.findByTaiKhoan(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        Set<GrantedAuthority> authorities = new HashSet<>();

        if (nguoiDung.getNhanVien() != null && nguoiDung.getNhanVien().getVaiTro() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + nguoiDung.getNhanVien().getVaiTro().toUpperCase()));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(
                nguoiDung.getTaiKhoan(),
                nguoiDung.getMatKhau(),
                authorities
        );
    }
}

