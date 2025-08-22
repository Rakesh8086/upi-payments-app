package com.upi.upi_payments.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String phoneNumber;
    private String password;
}
