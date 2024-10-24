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
@Table(name = "SanPham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_SanPham")
    private int id;

    @Column(name = "Ma_San_Pham", nullable = false)
    private String maSanPham;

    @Column(name = "Ten_San_Pham", nullable = false)
    private String tenSanPham;

    @Column(name = "Ngay_Tao")
    private Date ngayTao;

    @Column(name = "Nguoi_Tao")
    private String nguoiTao;

    @Column(name = "Lan_Cap_Nhat_Cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "TrangThai")
    private int trangThai;

}
