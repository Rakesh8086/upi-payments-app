package com.upi.upi_payments.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransferRequestDTO {
 
    @NotBlank(message = "Receiver Phone number cannot be empty")
    @Size(min = 10, max=10, message = "Phone number must be exactly 10 digits")
    private String receiverPhoneNumber;

    @NotNull(message = "Transfer amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Transfer amount must be greater than 0")
    private BigDecimal amount;

};

