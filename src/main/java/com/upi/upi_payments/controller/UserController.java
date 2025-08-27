package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.dto.LoginResponseDTO;
import com.upi.upi_payments.dto.RegistrationRequestDTO;
import com.upi.upi_payments.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// controller is like a front desk. Handles requests, sends respones to service layer
// notice how every layer is separated - "Separation of concerns" - easy to test, debug

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody 
    RegistrationRequestDTO request) {
        userService.registerUser(request);
        
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO resp = userService.loginUser(request);
        
        return ResponseEntity.ok(resp);
    }
}
