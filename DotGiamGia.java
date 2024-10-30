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
@Table(name = "dot_giam_gia")
public class DotGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ten_dot")
    private String tenDot;

    @Column(name = "gia_tri_giam")
    private BigDecimal giaTriGiam;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "ngay_tao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date ngayTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "lan_cap_nhat_cuoi", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date lanCapNhatCuoi;

    @Column(name = "trang_thai")
    private int trangThai;
}
