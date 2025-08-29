package com.upi.upi_payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.upi.upi_payments.repository.UserRepository;
import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.entity.User;


@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginRequestDTO request){
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        if(!passwordEncoder.matches(request.getUserProvidedKey(), user.getUserProvidedKey())){
            throw new RuntimeException("Invalid credentials");
        }

        // Return the user's API key upon successful login for future use
        return user.getUserProvidedKey();
    }
}