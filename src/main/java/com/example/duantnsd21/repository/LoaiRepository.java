package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.Loai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiRepository extends JpaRepository<Loai, Integer> {
}