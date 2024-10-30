package com.example.duantnsd21.controller;


import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.entity.SanPhamDTO;
import com.example.duantnsd21.repository.SanPhamChiTietRepository;
import com.example.duantnsd21.repository.SanPhamRp;
import com.example.duantnsd21.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService service;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @GetMapping("/hien-thi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SanPham>> getAllSanPham() {
        try {
            List<SanPham> sanPhamList = service.getAll();

            return new ResponseEntity<>(sanPhamList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    @GetMapping("/top-selling")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingProductsByBrand(@RequestParam(required = false) String brandName) {
        List<Object[]> results = service.getTopSellingProducts(brandName);

        List<Map<String, Object>> topSellingProducts = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", row[0]);
            product.put("tenSanPham", row[1]);
            product.put("thuongHieu", row[2]);
            product.put("tongSoLuongBan", row[3]);
            product.put("giaTien", row[4]);
            product.put("hinhAnh", row[5]);
            topSellingProducts.add(product);
        }

        return ResponseEntity.ok(topSellingProducts);
    }
    @GetMapping("/index-category")
    public String getProductsPage() {
        return "products";
    }
    @GetMapping("/category")
    public ResponseEntity<Page<SanPham>> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        Page<SanPham> productsPage = service.findProductsByCategory(category, PageRequest.of(page, size));
        return new ResponseEntity<>(productsPage, HttpStatus.OK);
    }

    @GetMapping("/by-brand")
    public ResponseEntity<List<SanPhamChiTiet>> getSanPhamByThuongHieu(@RequestParam String brandName) {
        List<SanPhamChiTiet> sanPhamChiTietList = service.getSanPhamChiTietByThuongHieu(brandName);
        return ResponseEntity.ok(sanPhamChiTietList);
    }

}
