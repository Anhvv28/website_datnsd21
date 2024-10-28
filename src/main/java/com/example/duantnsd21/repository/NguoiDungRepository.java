package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByEmail(String email);
    Optional<NguoiDung> findByTaiKhoan(String taiKhoan);
    Optional<NguoiDung> findByTaiKhoanOrEmail(String taiKhoan, String email);

}

