package com.example.duantnsd21.service;

import com.example.duantnsd21.ResourceNotFoundException;
import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.SanPham;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.entity.YeuThich;
import com.example.duantnsd21.repository.NguoiDungRepository;
import com.example.duantnsd21.repository.SanPhamChiTietRepository;
import com.example.duantnsd21.repository.SanPhamRp;
import com.example.duantnsd21.repository.YeuThichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class YeuThichService {

    @Autowired
    private YeuThichRepository yeuThichRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    /**
     * Thêm sản phẩm vào danh sách yêu thích của người dùng
     *
     * @param username ID của người dùng
     * @param sanPhamChiTietId   ID của sản phẩm
     * @return YeuThich object đã được lưu
     */
    public void addFavoriteProduct(String username, Integer sanPhamChiTietId) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        YeuThich yeuThich = new YeuThich(nguoiDung, sanPhamChiTiet);
        yeuThichRepository.save(yeuThich);
    }

    /**
     * Loại bỏ sản phẩm khỏi danh sách yêu thích của người dùng
     *
     * @param username ID của người dùng
     * @param sanPhamChiTietId   ID của sản phẩm
     */
    public void removeFavorite(String username, Integer sanPhamChiTietId) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        YeuThich yeuThich = yeuThichRepository.findByNguoiDungAndSanPhamChiTiet(nguoiDung, sanPhamChiTiet)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found for this user and product"));

        yeuThichRepository.delete(yeuThich);
    }

    public boolean isFavorited(String username, Integer sanPhamChiTietId) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung", "username", username));

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId)
                .orElseThrow(() -> new ResourceNotFoundException("SanPhamChiTiet", "id", sanPhamChiTietId));

        return yeuThichRepository.existsByNguoiDungAndSanPhamChiTiet(nguoiDung, sanPhamChiTiet);
    }





    /**
     * Lấy danh sách yêu thích của người dùng
     *
     * @param nguoiDungId ID của người dùng
     * @return List YeuThich
     */
    public List<YeuThich> getFavoritesByNguoiDung(Integer nguoiDungId) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(nguoiDungId)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung", "id", nguoiDungId));

        return yeuThichRepository.findByNguoiDung(nguoiDung);
    }
}

