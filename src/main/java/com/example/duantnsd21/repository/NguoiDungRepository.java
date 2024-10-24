package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    NguoiDung findByEmail(String email);
    NguoiDung findByTaiKhoan(String taiKhoan);

}

