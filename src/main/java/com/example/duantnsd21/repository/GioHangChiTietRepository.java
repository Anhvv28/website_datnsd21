package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.GioHang;
import com.example.duantnsd21.entity.GioHangChiTiet;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {
    Optional<GioHangChiTiet> findByGioHangAndSanPhamChiTiet(GioHang gioHang, SanPhamChiTiet sanPhamChiTiet);
}

