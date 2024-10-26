package com.example.duantnsd21.controller;


import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.repository.SanPhamRp;
import com.example.duantnsd21.service.SaPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    @Autowired
    private SaPhamService service;
    @Autowired
    private SanPhamRp productRepository;

    @GetMapping("/hien-thi")
    @PreAuthorize("hasRole('ADMIN')") // Chỉ cho phép ADMIN truy cập
    public ResponseEntity<List<SanPham>> getAllSanPham() {
        try {
            List<SanPham> sanPhamList = service.getAll();
            return new ResponseEntity<>(sanPhamList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingProductsByBrand(@RequestParam String brandName) {
        List<Object[]> results = productRepository.findTopSellingProductsByBrand(brandName);
        List<Map<String, Object>> topSellingProducts = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> product = new HashMap<>();
            product.put("tenSanPham", row[0]);
            product.put("thuongHieu", row[1]);
            product.put("tongSoLuongBan", row[2]);
            product.put("giaTien", row[3]);
            product.put("hinhAnh", row[4]);
            topSellingProducts.add(product);
        }

        return ResponseEntity.ok(topSellingProducts);
    }

}
