package com.upi.upi_payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.service.AuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDTO request) {
        // System.out.println("-----> LOGIN ENDPOINT HIT <-----");
        // System.out.println("Received Login Request for Phone: " + request.getPhoneNumber());
        String userProvidedKey = authenticationService.login(request);
        // System.out.println("Login Successful. Returning user provided key.");
        return ResponseEntity.ok(userProvidedKey);
    }
}