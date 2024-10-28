package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.entity.YeuThich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YeuThichRepository extends JpaRepository<YeuThich, Integer> {
    Optional<YeuThich> findByNguoiDungAndSanPhamChiTiet(NguoiDung nguoiDung, SanPhamChiTiet sanPhamChiTiet);
    boolean existsByNguoiDungAndSanPhamChiTiet(NguoiDung nguoiDung, SanPhamChiTiet sanPhamChiTiet);
    boolean existsByNguoiDungIdAndSanPhamChiTietId(Integer nguoiDungId, Integer sanPhamChiTietId);
    List<YeuThich> findByNguoiDung(NguoiDung nguoiDung);
}

