package com.upi.upi_payments.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upi.upi_payments.dto.TransferRequestDTO;
import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.service.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wallets")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> userToUserTransaction(
            @Valid @RequestBody TransferRequestDTO request){
        
        Transaction transaction = transferService.performTransaction(
            request.getSenderPhoneNumber(),
            request.getReceiverPhoneNumber(),
            request.getAmount()
        );

        return ResponseEntity.ok(transaction);
    }
}
