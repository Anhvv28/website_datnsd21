package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRp extends JpaRepository<SanPham, Integer> {

    @Query("SELECT sp.id, sp.tenSanPham, th.ten, SUM(hdct.soLuong) AS tongSoLuongBan, MAX(spct.giaTien), MAX(ha.duongDan) " +
            "FROM SanPham sp " +
            "JOIN SanPhamChiTiet spct ON sp.id = spct.sanPham.id " +
            "JOIN ThuongHieu th ON spct.thuongHieu.id = th.id " +
            "JOIN HoaDonChiTiet hdct ON spct.id = hdct.sanPhamChiTiet.id " +
            "LEFT JOIN HinhAnh ha ON spct.id = ha.sanPhamChiTiet.id " +
            "WHERE th.ten = :brandName " +
            "GROUP BY sp.id, sp.tenSanPham, th.ten ")
    List<Object[]> findTop8SellingProductsByBrand(String brandName);



    @Query("SELECT sp.id, sp.tenSanPham, th.ten, SUM(hdct.soLuong) AS tongSoLuongBan, MAX(spct.giaTien), MAX(ha.duongDan) " +
            "FROM SanPham sp " +
            "JOIN SanPhamChiTiet spct ON sp.id = spct.sanPham.id " +
            "JOIN ThuongHieu th ON spct.thuongHieu.id = th.id " +
            "JOIN HoaDonChiTiet hdct ON spct.id = hdct.sanPhamChiTiet.id " +
            "LEFT JOIN HinhAnh ha ON spct.id = ha.sanPhamChiTiet.id " +
            "GROUP BY sp.id, sp.tenSanPham, th.ten " +
            "ORDER BY tongSoLuongBan DESC")
    List<Object[]> findAllTop8SellingProducts();



    @Query("SELECT sp FROM SanPham sp JOIN SanPhamChiTiet spct ON sp.id = spct.sanPham.id " +
            "WHERE LOWER(spct.gioiTinh) = LOWER(:gioiTinh)")
    Page<SanPham> findByCategory(@Param("gioiTinh") String gioiTinh, Pageable pageable);
}
