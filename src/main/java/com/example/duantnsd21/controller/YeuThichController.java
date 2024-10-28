package com.example.duantnsd21.controller;

import com.example.duantnsd21.DuplicateFavoriteException;
import com.example.duantnsd21.ResourceNotFoundException;
import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.SanPhamChiTiet;
import com.example.duantnsd21.entity.YeuThich;
import com.example.duantnsd21.repository.NguoiDungRepository;
import com.example.duantnsd21.repository.SanPhamChiTietRepository;
import com.example.duantnsd21.repository.YeuThichRepository;
import com.example.duantnsd21.service.YeuThichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;




import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/favorites")
public class YeuThichController {

    @Autowired
    private YeuThichService yeuThichService;

    @Autowired
    private YeuThichRepository yeuThichRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;


    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestParam Integer sanPhamChiTietId, Principal principal) {
        String username = principal.getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung", "username", username));

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId)
                .orElseThrow(() -> new ResourceNotFoundException("SanPhamChiTiet", "id", sanPhamChiTietId));

        if (yeuThichRepository.existsByNguoiDungAndSanPhamChiTiet(nguoiDung, sanPhamChiTiet)) {
            throw new DuplicateFavoriteException("Sản phẩm đã tồn tại trong danh sách yêu thích của bạn");
        }

        YeuThich yeuThich = new YeuThich();
        yeuThich.setNguoiDung(nguoiDung);
        yeuThich.setSanPhamChiTiet(sanPhamChiTiet);
        yeuThichRepository.save(yeuThich);

        return ResponseEntity.ok("Đã thêm vào danh sách yêu thích");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestParam Integer sanPhamChiTietId, Principal principal) {
        String username = principal.getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung", "username", username));

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId)
                .orElseThrow(() -> new ResourceNotFoundException("SanPhamChiTiet", "id", sanPhamChiTietId));

        YeuThich yeuThich = yeuThichRepository.findByNguoiDungAndSanPhamChiTiet(nguoiDung, sanPhamChiTiet)
                .orElseThrow(() -> new ResourceNotFoundException("YeuThich", "sanPhamChiTietId", sanPhamChiTietId));

        yeuThichRepository.delete(yeuThich);

        return ResponseEntity.ok("Đã bỏ yêu thích");
    }

    @GetMapping("/is-favorited")
    public ResponseEntity<Boolean> isFavorited(@RequestParam Integer sanPhamChiTietId, Principal principal) {
        String username = principal.getName();
        boolean isFavorited = yeuThichService.isFavorited(username, sanPhamChiTietId);
        return ResponseEntity.ok(isFavorited);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Integer>> getUserFavorites(Principal principal) {
        String username = principal.getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung", "username", username));

        List<Integer> favoriteProductIds = yeuThichRepository.findByNguoiDung(nguoiDung).stream()
                .map(yeuThich -> yeuThich.getSanPhamChiTiet().getId()) // Lấy ID của chi tiết sản phẩm
                .collect(Collectors.toList());

        return ResponseEntity.ok(favoriteProductIds);
    }





}
