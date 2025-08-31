package com.upi.upi_payments.service;

import com.upi.upi_payments.dto.RegistrationRequestDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
// Every single step of the process has to saved in DB
// After updation, the updated state of the entity has to set using SET method
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationRequestDTO request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserType(request.getUserType());
        user.setCreatedAt(LocalDateTime.now());
        
        // System.out.println("Plain Text Password Before Hashing: " + request.getPassword());
        // System.out.println("Plain Text User Key Before Hashing: " + request.getUserProvidedKey());
        
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserProvidedKey(passwordEncoder.encode(request.getUserProvidedKey()));
        
        // System.out.println("Hashed Password: " + user.getPassword());
        // System.out.println("Hashed User Key: " + user.getUserProvidedKey());
        
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setUpdatedAt(LocalDateTime.now());
        
        wallet.setUser(user);
        user.setWallet(wallet);

        return userRepository.save(user);
    }
}