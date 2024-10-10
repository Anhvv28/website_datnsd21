package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByEmail(String email);
}
