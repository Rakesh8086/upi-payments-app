package com.upi.upi_payments.config.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.upi.upi_payments.entity.Session;
import com.upi.upi_payments.repository.SessionRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionRepository sessionRepository;

    // public endpoints that don't need a session
    private static final List<String> PUBLIC_PATHS = List.of(
        "/api/users/register", "/api/users/login"
    );

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // skip public endpoints
        if (PUBLIC_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("X-Session-Token");
        if (token == null || token.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing session token");
            return;
        }

        Optional<Session> optSession = sessionRepository.findByToken(token);
        if (optSession.isEmpty() || optSession.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired session token");
            return;
        }

        // attach user to request so controllers can access it if needed
        request.setAttribute("authenticatedUser", optSession.get().getUser());

        filterChain.doFilter(request, response);
    }
}