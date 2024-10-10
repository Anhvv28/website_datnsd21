package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "ngay_xac_nhan")
    private Date ngayXacNhan;

    @Column(name = "ngay_van_chuyen")
    private Date ngayVanChuyen;

    @Column(name = "ngay_hoan_thanh")
    private Date ngayHoanThanh;

    @Column(name = "loai_hoa_don")
    private String loaiHoaDon;

    @Column(name = "ngay_tao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private int trangThai;

    // Getters và setters
}