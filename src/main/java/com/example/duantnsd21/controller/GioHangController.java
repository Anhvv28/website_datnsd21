package com.example.duantnsd21.controller;

import com.example.duantnsd21.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for handling shopping cart operations.
 */
@RestController
@RequestMapping("/api/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    /**
     * Endpoint to add a product to the shopping cart.
     *
     * @param requestData     JSON payload containing "productId".
     * @param authentication  Spring Security authentication object.
     * @return ResponseEntity with appropriate status and message.
     */
    @PostMapping("/them")
    public ResponseEntity<?> themVaoGioHang(@RequestBody Map<String, Integer> requestData,
                                            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Bạn cần đăng nhập để thêm vào giỏ hàng."));
        }

        Integer productId = requestData.get("productId");
        if (productId == null) {
            return ResponseEntity.status(400)
                    .body(Map.of("error", "Thiếu productId trong yêu cầu."));
        }

        String identifier = getUserIdentifier(authentication);
        if (identifier == null || identifier.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Bạn cần đăng nhập để thêm vào giỏ hàng."));
        }

        gioHangService.themVaoGioHang(identifier, productId);
        return ResponseEntity.ok(Map.of("message", "Sản phẩm đã được thêm vào giỏ hàng!"));
    }

    /**
     * Retrieves the user's identifier from the Authentication object.
     *
     * @param authentication The Spring Security authentication object.
     * @return The user's identifier (username or email), or null if not found.
     */
    private String getUserIdentifier(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            return ((OAuth2User) principal).getAttribute("email");
        }
        return null;
    }
}


