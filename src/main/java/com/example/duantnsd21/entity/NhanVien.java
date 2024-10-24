package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_nv")
    private String maNv;

    @Column(name = "vai_tro")
    private String vaiTro;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "lan_cap_nhat_cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "trangThai")
    private int trangThai;

    @OneToOne
    @JoinColumn(name = "Nguoi_dung_Id") // Đảm bảo tên cột là 'NguoiDung_Id'
    private NguoiDung nguoiDung;

}

