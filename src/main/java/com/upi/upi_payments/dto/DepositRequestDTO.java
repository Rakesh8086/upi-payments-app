package com.upi.upi_payments.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DepositRequestDTO {
    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max=10, message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    
    @NotNull(message = "Deposit amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Deposit amount must be greater than 0")
    private BigDecimal amount;

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
    public BigDecimal getAmount(){
        return amount;
    }
};
