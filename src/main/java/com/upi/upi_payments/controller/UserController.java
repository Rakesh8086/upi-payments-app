package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.UserRegistrationDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// controller is like a front desk. Handles requests, sends respones to service layer
// notice how every layer is separated - "Separation of concerns" - easy to test, debug

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO request){
        return userService.registerUser(
            request.getUserName(),
            request.getPhoneNumber(),
            request.getUserType()
        );
    }
}
