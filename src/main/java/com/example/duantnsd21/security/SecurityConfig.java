package com.example.duantnsd21.security;

import com.example.duantnsd21.filter.UserExistenceCheckFilter;
import com.example.duantnsd21.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserExistenceCheckFilter userExistenceCheckFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    private static final String[] PUBLIC_ENDPOINTS = {
            "/", "/css/**", "/js/**", "/images/**", "/login-form", "/api/san-pham/top-selling","/favicon.png",
            "/api/san-pham/category","api/san-pham/index-category", "/api/user-info","/oauth2/**","/api/chatbot", "/chatbot", "/other-open-endpoints",
            "/api/san-pham/nike-product"
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/admin/**", "/api/san-pham/hien-thi"
    };

    private static final String[] EMPLOYEE_ENDPOINTS = {
            "/employee/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(EMPLOYEE_ENDPOINTS).hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login-form")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login-form?error=true")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .formLogin(form -> form
                        .loginPage("/login-form")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .failureUrl("/login-form?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "authToken")
                        .permitAll()
                );
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Thêm dòng này
//        http.addFilterBefore(userExistenceCheckFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


}
