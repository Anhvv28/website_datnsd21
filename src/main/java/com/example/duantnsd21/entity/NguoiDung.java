package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "nguoi_dung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "trang_thai")
    private int trangThai;

    @OneToOne(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Để tránh vòng lặp khi serialize
    private List<YeuThich> yeuThichList = new ArrayList<>();
}
