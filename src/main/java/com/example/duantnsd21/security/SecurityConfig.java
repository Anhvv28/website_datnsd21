package com.example.duantnsd21.security;

import com.example.duantnsd21.service.CustomAuthenticationSuccessHandler;
import com.example.duantnsd21.service.CustomOAuth2UserService;
import com.example.duantnsd21.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    // Authentication provider bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .authenticationProvider(authenticationProvider())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/login-form", "/api/user-info").permitAll()
                        .requestMatchers("/admin/**","/").hasRole("ADMIN")
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()) // Add this line
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
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }


}
