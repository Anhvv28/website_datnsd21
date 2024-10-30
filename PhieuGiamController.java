package com.example.duantnsd21.controller;

import com.example.duantnsd21.entity.PhieuGiam;
import com.example.duantnsd21.service.PhieuGiamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieu-giam")
public class PhieuGiamController {

    @Autowired
    private PhieuGiamService phieuGiamService;

    @GetMapping("/hien-thi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PhieuGiam>> getAllPhieuGiam() {
        try {
            List<PhieuGiam> phieuGiamList = phieuGiamService.getAllPG();
            return new ResponseEntity<>(phieuGiamList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/them")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PhieuGiam> addPhieuGiam(@RequestBody PhieuGiam phieuGiam) {
        try {
            // Ghi lại yêu cầu nhận được để dễ dàng gỡ lỗi
            System.out.println("Nhận yêu cầu thêm PhieuGiam: " + phieuGiam);

            PhieuGiam createdPhieuGiam = phieuGiamService.themPhieuGiam(phieuGiam);

            return new ResponseEntity<>(createdPhieuGiam, HttpStatus.CREATED);
        } catch (Exception e) {
            // Ghi lại lỗi
            System.err.println("Lỗi khi tạo PhieuGiam: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sua/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PhieuGiam> updatePhieuGiam(@PathVariable Integer id, @RequestBody PhieuGiam phieuGiamMoi) {
        try {
            PhieuGiam updatedPhieuGiam = phieuGiamService.suaPhieuGiam(id, phieuGiamMoi);
            return ResponseEntity.ok(updatedPhieuGiam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PhieuGiam> getPhieuGiamById(@PathVariable Integer id) {
        try {
            PhieuGiam phieuGiam = phieuGiamService.getPhieuGiamById(id);
            return new ResponseEntity<>(phieuGiam, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }





}
