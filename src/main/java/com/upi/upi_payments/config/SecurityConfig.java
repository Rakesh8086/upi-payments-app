package com.upi.upi_payments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.upi.upi_payments.config.filter.SessionFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SessionFilter sessionFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for API testing (ok for dev)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                                .anyRequest().authenticated()
                );

        // ensure your session filter runs before Spring's auth processing
        http.addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}