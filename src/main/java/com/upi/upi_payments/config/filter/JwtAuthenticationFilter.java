package com.upi.upi_payments.config.filter;

import com.upi.upi_payments.service.CustomUserDetailsService;
import com.upi.upi_payments.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * This method is the core of the filter. It's called for every incoming request.
     * It checks for a valid JWT token in the Authorization header and authenticates the user.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String phoneNumber;

        // If the Authorization header is missing or doesn't start with "Bearer ",
        // we let the request continue without authentication.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT from the header
        jwt = authHeader.substring(7);

        // Extract the phone number from the JWT using our JwtService
        phoneNumber = jwtService.extractUsername(jwt);

        // If the phone number is not null and the user is not already authenticated
        if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the database using the phone number
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNumber);

            // Validate the token against the user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // If the token is valid, create an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Update the SecurityContextHolder with the new authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
