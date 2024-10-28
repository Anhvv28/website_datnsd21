package com.example.duantnsd21.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_san_pham", nullable = false)
    private String maSanPham;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;
//

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "lan_cap_nhat_cuoi")
    private Date lanCapNhatCuoi;

    @Column(name = "trang_thai")
    private int trangThai;


    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SanPhamChiTiet> sanPhamChiTiet= new ArrayList<>();


}
