package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRp extends JpaRepository<SanPham, Integer> {
}
