package com.example.duantnsd21.repository;

import com.example.duantnsd21.entity.ChatLieu;
import com.example.duantnsd21.entity.DeGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {

}
