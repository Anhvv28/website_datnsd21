package com.example.duantnsd21.controller;

import com.example.duantnsd21.entity.DotGiamGia;
import com.example.duantnsd21.service.DotGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dot-giam-gia")
public class DotGiamGiaController {

    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    @GetMapping("/hien-thi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DotGiamGia>> getAllDotGiamGia() {
        try {
            List<DotGiamGia> dotGiamGiaList = dotGiamGiaService.getAllDGG();
            return new ResponseEntity<>(dotGiamGiaList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/them")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DotGiamGia> addDotGiamGia(@RequestBody DotGiamGia dotGiamGia) {
        try {
            // Ghi lại yêu cầu nhận được để dễ dàng gỡ lỗi
            System.out.println("Nhận yêu cầu thêm DotGiamGia: " + dotGiamGia);

            DotGiamGia createdDotGiamGia = dotGiamGiaService.themDotGiamGia(dotGiamGia);

            return new ResponseEntity<>(createdDotGiamGia, HttpStatus.CREATED);
        } catch (Exception e) {
            // Ghi lại lỗi
            System.err.println("Lỗi khi tạo DotGiamGia: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sua/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DotGiamGia> updateDotGiamGia(@PathVariable Integer id, @RequestBody DotGiamGia dotGiamGiaMoi) {
        try {
            DotGiamGia updatedDotGiamGia = dotGiamGiaService.suaDotGiamGia(id, dotGiamGiaMoi);
            return ResponseEntity.ok(updatedDotGiamGia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DotGiamGia> getDotGiamGiaById(@PathVariable Integer id) {
        try {
            DotGiamGia dotGiamGia = dotGiamGiaService.getDotGiamGiaById(id);
            return new ResponseEntity<>(dotGiamGia, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
