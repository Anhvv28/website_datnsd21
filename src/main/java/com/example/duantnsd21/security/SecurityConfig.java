package com.example.duantnsd21.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Allow public access to the home page ("/") and static resources (e.g., CSS/JS/images)
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                        // Allow access to OAuth2 login routes
                        .requestMatchers("/oauth2/**").permitAll()
                        // Require authentication for any other requests
                        .anyRequest().authenticated()
                )
                // Enable OAuth2 login for Google and Facebook
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Set the home page as the login page (no auto-redirect)
                        .defaultSuccessUrl("/") // After login, redirect to home page
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // After logout, redirect to home page
                        .permitAll());

        return http.build();
    }
}
