package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.repository.SanPhamRp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRp sanPhamRp;

    public List<SanPham> getAll() {
        return sanPhamRp.findAll();
    }

    public List<Object[]> getTopSellingProducts(String brandName) {
        if (brandName == null || brandName.isEmpty() || brandName.equalsIgnoreCase("All")) {
            return sanPhamRp.findAllTopSellingProducts();
        } else {
            return sanPhamRp.findTopSellingProductsByBrand(brandName);
        }
    }
}
