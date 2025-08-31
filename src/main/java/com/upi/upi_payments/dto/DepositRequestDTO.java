package com.upi.upi_payments.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepositRequestDTO {
    
    @NotNull(message = "Deposit amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Deposit amount must be greater than 0")
    private BigDecimal amount;

};
