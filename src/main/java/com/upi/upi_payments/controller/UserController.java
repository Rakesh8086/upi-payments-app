package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.UserRegistrationDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDTO request){
        User user = userService.registerUser(
            request.getUserName(),
            request.getPhoneNumber(),
            request.getUserType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
