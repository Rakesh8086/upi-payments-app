package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.dto.UserRegistrationDTO;
import com.upi.upi_payments.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint for user registration.
     * @param userRegistrationDTO The DTO containing user registration data.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        authenticationService.register(userRegistrationDTO);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Endpoint for user login.
     * @param loginRequestDTO The DTO containing user login credentials.
     * @return A ResponseEntity with the generated JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Log the incoming phone number and password for debugging
        System.out.println("Received login request for phone number: " + loginRequestDTO.getPhoneNumber());
        System.out.println("Received password: " + loginRequestDTO.getPassword());

        String token = authenticationService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }
}
