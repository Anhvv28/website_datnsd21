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
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "SanPham_Id", nullable = false)
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "ThuongHieu_Id", nullable = false)
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "ChatLieu_Id", nullable = false)
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "DeGiay_Id", nullable = false)
    private DeGiay deGiay;

    @ManyToOne
    @JoinColumn(name = "Loai_Id", nullable = false)
    private Loai loai;

    @Column(name = "Ma_San_Pham_CT", nullable = false)
    private String maSanPhamCT;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_luong", nullable = false)
    private int soLuong;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "kich_co")
    private String kichCo;

    @Column(name = "mau_sac")
    private String mauSac;

    @Column(name = "gia_tien", nullable = false)
    private BigDecimal giaTien;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "lan_cap_nhat_cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "trang_thai")
    private int trangThai;
}
