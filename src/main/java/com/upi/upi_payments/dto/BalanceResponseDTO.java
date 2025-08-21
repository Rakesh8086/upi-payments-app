package com.upi.upi_payments.dto;

import java.math.BigDecimal;

// This DTO is used to send the user's balance back in the API response.
public class BalanceResponseDTO {
    
    private String phoneNumber;
    private BigDecimal balance;

    public BalanceResponseDTO(String phoneNumber, BigDecimal balance) {
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
