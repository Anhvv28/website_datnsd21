package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.PhieuGiam;
import com.example.duantnsd21.repository.PhieuGiamRepository;
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
public class PhieuGiamService {

    @Autowired
    private PhieuGiamRepository phieuGiamRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PhieuGiam> getAllPG() {
        if (phieuGiamRepository == null) {
            throw new IllegalStateException("PhieuGiamRepository lỗi!!!!");
        }
        return phieuGiamRepository.findAll();
    }

    public PhieuGiam themPhieuGiam(PhieuGiam phieuGiam) {
        // Lấy tên tài khoản của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Thiết lập các trường tự động
        phieuGiam.setNguoiTao(currentUsername);
        phieuGiam.setNgayTao(new Date());
        phieuGiam.setNguoiCapNhat(currentUsername);
        phieuGiam.setLanCapNhatCuoi(new Date());

        // SQL query để thêm mới PhieuGiam vào cơ sở dữ liệu
        String sql = "INSERT INTO phieu_giam (ma_giam, gia_tri_giam_max, gia_tri_giam, so_luong, ngay_bat_dau, ngay_ket_thuc, nguoi_tao, ngay_tao, nguoi_cap_nhat, lan_cap_nhat_cuoi, trang_thai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Thực thi lệnh SQL
        jdbcTemplate.update(sql,
                phieuGiam.getMaGiam(),
                phieuGiam.getGiaTriGiamMax(),
                phieuGiam.getGiaTriGiam(),
                phieuGiam.getSoLuong(),
                phieuGiam.getNgayBatDau(),
                phieuGiam.getNgayKetThuc(),
                currentUsername,           // người tạo
                LocalDateTime.now(),       // ngày tạo
                currentUsername,
                LocalDateTime.now(),
                phieuGiam.getTrangThai()    // trạng thái
        );

        // Trả về đối tượng PhieuGiam đã thêm để sử dụng sau (nếu cần)
        return phieuGiam;
    }

    public PhieuGiam suaPhieuGiam(Integer id, PhieuGiam phieuGiamMoi) {
        Optional<PhieuGiam> optionalPhieuGiam = phieuGiamRepository.findById(id);
        if (optionalPhieuGiam.isEmpty()) {
            throw new EntityNotFoundException("Phiếu giảm không tồn tại");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        PhieuGiam phieuGiam = optionalPhieuGiam.get();
        phieuGiam.setMaGiam(phieuGiamMoi.getMaGiam());
        phieuGiam.setGiaTriGiamMax(phieuGiamMoi.getGiaTriGiamMax());
        phieuGiam.setGiaTriGiam(phieuGiamMoi.getGiaTriGiam());
        phieuGiam.setSoLuong(phieuGiamMoi.getSoLuong());
        phieuGiam.setNgayBatDau(phieuGiamMoi.getNgayBatDau());
        phieuGiam.setNgayKetThuc(phieuGiamMoi.getNgayKetThuc());
        phieuGiam.setNguoiTao(currentUsername);
        phieuGiam.setNgayTao(new Date());
        phieuGiam.setNguoiCapNhat(currentUsername);
        phieuGiam.setLanCapNhatCuoi(new Date());
        phieuGiam.setTrangThai(phieuGiamMoi.getTrangThai());

        return phieuGiamRepository.save(phieuGiam);
    }

    public PhieuGiam getPhieuGiamById(Integer id) {
        return phieuGiamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Phiếu giảm với ID: " + id));
    }
}
