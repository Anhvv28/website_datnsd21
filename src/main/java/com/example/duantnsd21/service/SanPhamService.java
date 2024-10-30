package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.repository.SanPhamChiTietRepository;
import com.example.duantnsd21.repository.SanPhamRp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRp sanPhamRp;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;


    public List<SanPham> getAll() {
        if (sanPhamRp == null) {
            throw new IllegalStateException("sanPhamRp is not properly initialized.");
        }
        return sanPhamRp.findAll();
    }

    public List<Object[]> getTopSellingProducts(String brandName) {
        if (brandName == null || brandName.isEmpty() || brandName.equalsIgnoreCase("All")) {
            return sanPhamRp.findAllTop8SellingProducts();
        } else {
            return sanPhamRp.findTop8SellingProductsByBrand(brandName);
        }
    }


    public Page<SanPham> findProductsByCategory(String category, Pageable pageable) {
        if ("nam".equalsIgnoreCase(category) || "nữ".equalsIgnoreCase(category)) {
            return sanPhamRp.findByCategory(category, pageable);
        } else {
            return Page.empty();
        }

    }

    public List<SanPhamChiTiet> getSanPhamChiTietByThuongHieu(String brandName) {
        return sanPhamChiTietRepository.findByThuongHieu_Ten(brandName);
    }
}
