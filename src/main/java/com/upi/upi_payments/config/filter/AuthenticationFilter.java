package com.upi.upi_payments.config.filter;

import com.upi.upi_payments.entity.Session;
import com.upi.upi_payments.repository.SessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SessionRepository sessionRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the token from the header
        String token = request.getHeader("X-Auth-Token");
        
        // Check if the request is for a public endpoint (like login/register)
        if (request.getRequestURI().contains("/api/auth/login") || request.getRequestURI().contains("/api/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check if token exists
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No token provided");
            return;
        }

        // Validate the session token
        Optional<Session> sessionOpt = sessionRepository.findByToken(token);

        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            // Check if the session is expired
            if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token expired");
                return;
            }
            // Token is valid, continue the filter chain
            filterChain.doFilter(request, response);
        } else {
            // Token is not found in the database
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
        }
    }
}