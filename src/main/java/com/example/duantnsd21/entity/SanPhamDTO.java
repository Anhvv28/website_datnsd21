package com.example.duantnsd21.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPhamDTO {
    private int id;
    private String tenSanPham;
    private double giaTien;
    private int soLuong;
    private List<String> mauSac;
    private String hinhAnh;
}
