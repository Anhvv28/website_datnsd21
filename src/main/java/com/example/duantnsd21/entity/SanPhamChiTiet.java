package com.example.duantnsd21.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @JoinColumn(name = "san_pham_id")
    @JsonBackReference
    private SanPham sanPham;


    @ManyToOne
    @JoinColumn(name = "thuong_hieu_id", nullable = false)
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "chat_lieu_id", nullable = false)
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "de_giay_id", nullable = false)
    private DeGiay deGiay;

    @ManyToOne
    @JoinColumn(name = "loai_id", nullable = false)
    private Loai loai;

    @ManyToOne
    @JoinColumn(name = "kich_co_id")
    private KichCo kichCo;

    @ManyToOne
    @JoinColumn(name = "mau_sac_id", nullable = false)
    private MauSac mauSac;

    @Column(name = "ma_spct", nullable = false)
    private String maSpct;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_luong", nullable = false)
    private int soLuong;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "gia_tien")
    private double giaTien;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "lan_cap_nhat_cuoi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lanCapNhatCuoi;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "trang_thai")
    private String trangThai;

    @OneToMany(mappedBy = "sanPhamChiTiet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HinhAnh> hinhAnh = new ArrayList<>();

    @OneToMany(mappedBy = "sanPhamChiTiet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Để tránh vòng lặp khi serialize
    private List<YeuThich> yeuThichList = new ArrayList<>();
}