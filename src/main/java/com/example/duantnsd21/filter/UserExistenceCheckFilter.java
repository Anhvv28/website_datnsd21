package com.example.duantnsd21.filter;

import com.example.duantnsd21.repository.NguoiDungRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserExistenceCheckFilter extends OncePerRequestFilter {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            if (!nguoiDungRepository.findByTaiKhoan(username).isPresent()) {
                // Xóa Security Context và chuyển hướng đến trang đăng nhập
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                // Xóa cookie 'authToken' nếu có
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("authToken".equals(cookie.getName())) {
                            cookie.setValue("");
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }

                response.sendRedirect("/login-form?userDeleted=true");
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
