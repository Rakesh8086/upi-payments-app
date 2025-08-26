package com.upi.upi_payments.service;

import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Session;
import com.upi.upi_payments.repository.SessionRepository;
import com.upi.upi_payments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRepository sessionRepository;

    public String login(LoginRequestDTO request){
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        // Generate a session token
        String token = UUID.randomUUID().toString();

        // Create and save the new session
        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(1)); // Session expires in 1 hours

        sessionRepository.save(session);

        // Return the token
        return token;
    }
}