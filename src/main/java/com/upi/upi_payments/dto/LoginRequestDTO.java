package com.upi.upi_payments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
    @NotBlank(message = "User provided key cannot be empty")
    private String userProvidedKey;
}