package com.example.duantnsd21.security;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.repository.NguoiDungRepository;
import com.example.duantnsd21.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final NguoiDungRepository nguoiDungRepository;
    private final JwtTokenProvider tokenProvider;

    public CustomOAuth2UserService(NguoiDungRepository nguoiDungRepository, JwtTokenProvider tokenProvider) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Lấy thông tin từ OAuth2User
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Kiểm tra xem người dùng đã tồn tại chưa
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findByEmail(email);
        NguoiDung nguoiDung;

        if (optionalNguoiDung.isEmpty()) {
            // Nếu người dùng chưa tồn tại, tạo mới
            nguoiDung = new NguoiDung();
            nguoiDung.setTaiKhoan(email);
            nguoiDung.setMatKhau("");  // OAuth2 không cần mật khẩu
            nguoiDung.setEmail(email);
            nguoiDung.setHoTen(name);
            nguoiDung = nguoiDungRepository.save(nguoiDung);
        } else {
            // Nếu người dùng đã tồn tại, cập nhật thông tin nếu cần
            nguoiDung = optionalNguoiDung.get();
            nguoiDung.setHoTen(name); // Có thể cập nhật thông tin khác nếu cần
            nguoiDungRepository.save(nguoiDung);
        }

        // Tạo JWT token cho người dùng sau khi đăng nhập thành công
        String token = tokenProvider.generateToken(email);

        // Lưu token vào Http response hoặc cookie để sử dụng cho các yêu cầu tiếp theo
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (response != null) {
            response.setHeader("Authorization", "Bearer " + token);

            // Hoặc lưu vào cookie
            Cookie authCookie = new Cookie("authToken", token);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(true);  // Chỉ nên dùng với HTTPS
            authCookie.setPath("/");
            response.addCookie(authCookie);
        }

        return oAuth2User;
    }
}

