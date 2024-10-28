package com.example.duantnsd21.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "san_pham_yeu_thich")
public class YeuThich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Liên kết với người dùng
    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private NguoiDung nguoiDung;

    // Liên kết với sản phẩm
    @ManyToOne
    @JoinColumn(name = "spct_id", nullable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    public YeuThich(NguoiDung nguoiDung, SanPhamChiTiet sanPhamChiTiet) {
        this.nguoiDung = nguoiDung;
        this.sanPhamChiTiet = sanPhamChiTiet;
    }
}
