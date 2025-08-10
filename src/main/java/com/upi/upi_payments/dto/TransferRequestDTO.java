package com.upi.upi_payments.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransferRequestDTO {
    @NotBlank(message = "Sender Phone number cannot be empty")
    @Size(min = 10, max=10, message = "Phone number must be exactly 10 digits")
    private String senderPhoneNumber;
    
    @NotBlank(message = "Receiver Phone number cannot be empty")
    @Size(min = 10, max=10, message = "Phone number must be exactly 10 digits")
    private String receiverPhoneNumber;

    @NotNull(message = "Transfer amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Transfer amount must be greater than 0")
    private BigDecimal amount;

    public void setSenderPhoneNumber(String phoneNumber){
        this.senderPhoneNumber = phoneNumber;
    }
    public String getSenderPhoneNumber(){
        return senderPhoneNumber;
    }

    public void setReceiverPhoneNumber(String phoneNumber){
        this.receiverPhoneNumber = phoneNumber;
    }
    public String getReceiverPhoneNumber(){
        return receiverPhoneNumber;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
    public BigDecimal getAmount(){
        return amount;
    }
};

