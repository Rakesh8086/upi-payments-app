package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.RegistrationRequestDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.service.UserRegistrationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// controller is like a front desk. Handles requests, sends respones to service layer
// notice how every layer is separated - "Separation of concerns" - easy to test, debug

@RestController
@RequestMapping("/api/users")
public class UserRegistrationController {
    @Autowired
    private UserRegistrationService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequestDTO request) {
        // System.out.println("-----> REGISTER ENDPOINT HIT <-----");
        // System.out.println("Received Registration Request for Phone: " + request.getPhoneNumber());
        User registeredUser = userService.registerUser(request);
        return ResponseEntity.ok(registeredUser);
    }
}
