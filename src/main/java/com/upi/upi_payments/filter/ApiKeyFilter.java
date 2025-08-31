package com.upi.upi_payments.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.upi.upi_payments.repository.UserRepository;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiKeyFilter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log messages for debugging
        // System.out.println("-----> API KEY FILTER RUNNING <-----");
        // System.out.println("Request URL: " + request.getRequestURL());
        
        // Skip public endpoints
        if (request.getRequestURI().contains("/api/users/register") || request.getRequestURI().contains("/api/auth/login")) {
            System.out.println("Public endpoint detected. Skipping API Key filter.");
            filterChain.doFilter(request, response);
            return;
        }

        String userProvidedKey = request.getHeader("X-API-Key");
        System.out.println("Received X-API-Key: " + userProvidedKey);

        if (userProvidedKey != null) {
            // Find ALL users to match the key(what if many users used same api key, so only)
            for (com.upi.upi_payments.entity.User user : userRepository.findAll()) {
                if (passwordEncoder.matches(userProvidedKey, user.getUserProvidedKey())) {
                    System.out.println("User found by matching API Key. Phone number: " + user.getPhoneNumber());
                    
                    UserDetails userDetails = new User(user.getPhoneNumber(), user.getPassword(), Collections.emptyList());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Security context populated. Request is now authenticated.");

                    // IMPORTANT: Pass the request to the next filter in the chain and exit
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            // System.out.println("User not found for provided API Key.");
        } else {
            // System.out.println("No X-API-Key header found. Request will be denied.");
        }
        
        // If authentication fails, or no key is provided, the request will be denied with a 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Invalid or missing API Key");
    }
}