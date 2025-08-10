package com.upi.upi_payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upi.upi_payments.dto.DepositRequestDTO;
import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.service.DepositService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wallets")
public class DepositController {
    @Autowired 
    private DepositService depositService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> depositTransaction(
            @Valid @RequestBody DepositRequestDTO request){
        
        Transaction transaction = depositService.deposit(
            request.getPhoneNumber(), 
            request.getAmount()
        );
        
        return ResponseEntity.ok(transaction);
    }
}
