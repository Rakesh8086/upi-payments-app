package com.upi.upi_payments.service;

import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.dto.LoginResponseDTO;
import com.upi.upi_payments.dto.RegistrationRequestDTO;
import com.upi.upi_payments.entity.Session;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.SessionRepository;
import com.upi.upi_payments.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

// Every single step of the process has to saved in DB
// After updation, the updated state of the entity has to set using SET method
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRepository sessionRepository;


    public User registerUser(RegistrationRequestDTO request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserType(request.getUserType());
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setUpdatedAt(LocalDateTime.now());

        wallet.setUser(user);
        user.setWallet(wallet);

        return userRepository.save(user);
    }

    @Transactional
    public LoginResponseDTO loginUser(LoginRequestDTO request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // generate token (UUID is fine for now)
        String token = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusHours(1); // session valid for 1 hour

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setCreatedAt(now);
        session.setExpiresAt(expiresAt);

        sessionRepository.save(session);

        return new LoginResponseDTO(token);
    }
}