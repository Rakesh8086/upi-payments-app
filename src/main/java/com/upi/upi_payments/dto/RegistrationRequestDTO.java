package com.upi.upi_payments.dto;

import com.upi.upi_payments.entity.UserType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    @NotNull(message = "User name cannot be null")
    @NotBlank(message = "User name cannot be blank")
    @Size(min = 3, max = 50, message = "User name must be between 3 and 50 characters")
    private String userName;
    
    @NotNull(message = "User type cannot be null")
    private UserType userType;
}