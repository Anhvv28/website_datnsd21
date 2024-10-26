package com.example.duantnsd21.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_san_pham", nullable = false)
    private String maSanPham;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;
//
    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "lan_cap_nhat_cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "trang_thai")
    private int trangThai;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<SanPhamChiTiet> sanPhamChiTiet;
}
