package com.upi.upi_payments.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.upi.upi_payments.entity.TransactionType;
import com.upi.upi_payments.entity.Status;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor // default empty constructor
@AllArgsConstructor // parameterized constructor
public class TransactionHistoryResponseDTO {
    private Long transactionId;
    private TransactionType transactionType; 
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private Status status;
    private String remarks;
}
