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
@Table(name = "Loai")
public class Loai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Ten", nullable = false)
    private String ten;

    @Column(name = "trang_thai")
    private int trangThai;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "lan_cap_nhat_cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

}
