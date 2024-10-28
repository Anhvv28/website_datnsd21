package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "gio_hang")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @Column(name = "ngay_tao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "trang_thai", nullable = false)
    private int trangThai;

//    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<GioHangChiTiet> gioHangChiTietList;


}
