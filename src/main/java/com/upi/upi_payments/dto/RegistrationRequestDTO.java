package com.upi.upi_payments.dto;

import com.upi.upi_payments.entity.UserType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    
    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @NotBlank(message = "User provided key cannot be empty")
    @Size(min = 4, max = 4, message = "User provided key must be 4 characters long")
    private String userProvidedKey;

    private UserType userType;
}