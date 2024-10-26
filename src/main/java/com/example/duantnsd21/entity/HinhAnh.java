package com.example.duantnsd21.entity;
import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "hinh_anh")
public class HinhAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "spct_id", nullable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "ten_anh")
    private String tenAnh;

    @Column(name = "duong_dan")
    private String duongDan;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "lan_cap_nhat_cuoi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lanCapNhatCuoi;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
