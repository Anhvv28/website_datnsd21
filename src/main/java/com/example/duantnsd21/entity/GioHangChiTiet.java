package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "gio_hang_id")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "spct_id")
    private SanPhamChiTiet sanPhamChiTiet;;

    @Column(name = "soluong", nullable = false)
    private int soLuong;

    @Column(name = "ngay_tao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "trang_thai", nullable = false)
    private int trangThai;


}
