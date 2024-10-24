package com.example.duantnsd21.service;

import com.example.duantnsd21.entity.NguoiDung;
import com.example.duantnsd21.entity.NhanVien;
import com.example.duantnsd21.repository.NguoiDungRepository;
import com.example.duantnsd21.repository.NhanVienRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final NguoiDungRepository nguoiDungRepository;

    public CustomOAuth2UserService(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        NguoiDung existingNguoiDung = nguoiDungRepository.findByEmail(email);

        if (existingNguoiDung == null) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setTaiKhoan(email);
            nguoiDung.setMatKhau("");
            nguoiDung.setEmail(email);
            nguoiDung.setHoTen(name);
            nguoiDungRepository.save(nguoiDung);
        }

        return oAuth2User;
    }
}

