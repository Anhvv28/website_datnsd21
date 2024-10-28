package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.GioHang;
import com.example.duantnsd21.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findByNguoiDungId(Integer nguoiDungId);
    Optional<GioHang> findByNguoiDung(NguoiDung nguoiDung);
}