package com.upi.upi_payments.dto;

import com.upi.upi_payments.entity.UserType;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String phoneNumber;
    private String password;
    private String userName;
    private UserType userType;
}