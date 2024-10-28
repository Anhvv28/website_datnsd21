package com.example.duantnsd21.service;

import com.example.duantnsd21.ProductAlreadyInCartException;
import com.example.duantnsd21.entity.GioHang;
import com.example.duantnsd21.entity.GioHangChiTiet;
import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.repository.GioHangChiTietRepository;
import com.example.duantnsd21.repository.GioHangRepository;
import com.example.duantnsd21.repository.NguoiDungRepository;
import com.example.duantnsd21.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    /**
     * Adds a product to the user's cart.
     *
     * @param identifier The user's identifier (username or email).
     * @param productId  The ID of the product to add.
     */
    @Transactional
    public void themVaoGioHang(String identifier, Integer productId) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoanOrEmail(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy người dùng với identifier: " + identifier));

        GioHang gioHang = layHoacTaoGioHangChoNguoiDung(nguoiDung);
        themSanPhamVaoGioHang(gioHang, productId);
    }

    /**
     * Retrieves the existing cart for the user or creates a new one if none exists.
     *
     * @param nguoiDung The user.
     * @return The user's cart.
     */
    private GioHang layHoacTaoGioHangChoNguoiDung(NguoiDung nguoiDung) {
        return gioHangRepository.findByNguoiDung(nguoiDung)
                .orElseGet(() -> {
                    GioHang gioHangMoi = new GioHang();
                    gioHangMoi.setNguoiDung(nguoiDung);
                    gioHangMoi.setNgayTao(new Date());
                    gioHangMoi.setTrangThai(1);
                    return gioHangRepository.save(gioHangMoi);
                });
    }

    /**
     * Adds a product to the cart. Throws an exception if the product is already in the cart.
     *
     * @param gioHang  The user's cart.
     * @param productId The ID of the product to add.
     */
    private void themSanPhamVaoGioHang(GioHang gioHang, Integer productId) {
        SanPhamChiTiet sanPham = sanPhamChiTietRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

        gioHangChiTietRepository.findByGioHangAndSanPhamChiTiet(gioHang, sanPham)
                .ifPresentOrElse(
                        gioHangChiTiet -> {
                            throw new ProductAlreadyInCartException("Sản phẩm đã tồn tại trong giỏ hàng.");
                        },
                        () -> {
                            GioHangChiTiet gioHangChiTietMoi = new GioHangChiTiet();
                            gioHangChiTietMoi.setGioHang(gioHang);
                            gioHangChiTietMoi.setSanPhamChiTiet(sanPham);
                            gioHangChiTietMoi.setSoLuong(1);
                            gioHangChiTietMoi.setNgayTao(new Date());
                            gioHangChiTietMoi.setTrangThai(1);
                            gioHangChiTietRepository.save(gioHangChiTietMoi);
                        }
                );
    }
}


