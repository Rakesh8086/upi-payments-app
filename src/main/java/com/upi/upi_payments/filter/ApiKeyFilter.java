package com.upi.upi_payments.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.upi.upi_payments.repository.UserRepository;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // parameterized constructor receives the dependencies and assigns them to the fields.
    public ApiKeyFilter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the API key from the header
        String userProvidedKey = request.getHeader("X-API-Key");

        // Check if the request is for a public endpoint
        if (request.getRequestURI().contains("/api/auth/login") || request.getRequestURI().contains("/api/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check if the key exists in the header
        if (userProvidedKey == null || userProvidedKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No API Key provided");
            return;
        }
        
        Optional<com.upi.upi_payments.entity.User> userOptionalForAuthentication = userRepository.findByUserProvidedKey(passwordEncoder.encode(userProvidedKey));

        if (userOptionalForAuthentication.isPresent()) {
            com.upi.upi_payments.entity.User authenticatedUser = userOptionalForAuthentication.get();
            
            // Create a UserDetails object from the found user.
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                authenticatedUser.getPhoneNumber(),
                authenticatedUser.getPassword(),
                Collections.emptyList()
            );
            
            // Create an authentication token and set it in the SecurityContext
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid API Key");
        }
    }
}
