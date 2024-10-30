package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.PhieuGiam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGiamRepository extends JpaRepository<PhieuGiam, Integer> {

}
