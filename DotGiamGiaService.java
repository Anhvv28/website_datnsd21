package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.DotGiamGia;
import com.example.duantnsd21.repository.DotGiamGiaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DotGiamGiaService {

    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DotGiamGia> getAllDGG() {
        if (dotGiamGiaRepository == null) {
            throw new IllegalStateException("DotGiamGiaRepository lỗi!!!!");
        }
        return dotGiamGiaRepository.findAll();
    }

    public DotGiamGia themDotGiamGia(DotGiamGia dotGiamGia) {
        // Lấy tên tài khoản của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Thiết lập các trường tự động
        dotGiamGia.setNguoiTao(currentUsername);
        dotGiamGia.setNgayTao(new Date());
        dotGiamGia.setNguoiCapNhat(currentUsername);
        dotGiamGia.setLanCapNhatCuoi(new Date());

        // SQL query để thêm mới DotGiamGia vào cơ sở dữ liệu
        String sql = "INSERT INTO dot_giam_gia (ten_dot) " +
                "VALUES (?)";

        // Thực thi lệnh SQL
        jdbcTemplate.update(sql,
                dotGiamGia.getTenDot()
//                dotGiamGia.getGiaTriGiam(),
//                dotGiamGia.getNgayBatDau(),
//                dotGiamGia.getNgayKetThuc(),
//                currentUsername,
//                LocalDateTime.now(),
//                currentUsername,
//                LocalDateTime.now(),
//                dotGiamGia.getTrangThai()
        );

        // Trả về đối tượng DotGiamGia đã thêm để sử dụng sau (nếu cần)
        return dotGiamGia;
    }

    public DotGiamGia suaDotGiamGia(Integer id, DotGiamGia dotGiamGiaMoi) {
        Optional<DotGiamGia> optionalDotGiamGia = dotGiamGiaRepository.findById(id);
        if (optionalDotGiamGia.isEmpty()) {
            throw new EntityNotFoundException("Đợt giảm giá không tồn tại");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        DotGiamGia dotGiamGia = optionalDotGiamGia.get();
        dotGiamGia.setTenDot(dotGiamGiaMoi.getTenDot());
        dotGiamGia.setGiaTriGiam(dotGiamGiaMoi.getGiaTriGiam());
        dotGiamGia.setNgayBatDau(dotGiamGiaMoi.getNgayBatDau());
        dotGiamGia.setNgayKetThuc(dotGiamGiaMoi.getNgayKetThuc());
        dotGiamGia.setNguoiTao(currentUsername);
        dotGiamGia.setNgayTao(new Date());
        dotGiamGia.setNguoiCapNhat(currentUsername);
        dotGiamGia.setLanCapNhatCuoi(new Date());
        dotGiamGia.setTrangThai(dotGiamGiaMoi.getTrangThai());

        return dotGiamGiaRepository.save(dotGiamGia);
    }

    public DotGiamGia getDotGiamGiaById(Integer id) {
        return dotGiamGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đợt giảm giá với ID: " + id));
    }
}
