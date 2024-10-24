package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    NhanVien findByNguoiDungId(Integer nguoiDungId);
    NhanVien findByNguoiDung(NguoiDung nguoiDung);

}

