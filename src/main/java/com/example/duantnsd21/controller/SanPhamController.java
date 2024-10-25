package com.example.duantnsd21.controller;


import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.service.SaPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SanPhamController {

    @Autowired
    private SaPhamService service;

    @GetMapping("/sanpham")
    public ResponseEntity<List<SanPham>> getAllSanPham() {
        try {
            List<SanPham> sanPhamList = service.getAll();
            return new ResponseEntity<>(sanPhamList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
