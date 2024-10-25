package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.repository.SanPhamRp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaPhamService {

    @Autowired
    private SanPhamRp sanPhamRp;

    public List<SanPham> getAll() {
        return sanPhamRp.findAll();
    }
}
