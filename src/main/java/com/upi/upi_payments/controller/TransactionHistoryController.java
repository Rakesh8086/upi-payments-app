package com.upi.upi_payments.controller;

import com.upi.upi_payments.dto.TransactionHistoryResponseDTO;
import com.upi.upi_payments.service.TransactionHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionHistoryController {

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<TransactionHistoryResponseDTO>> getTransactionHistory(@PathVariable String phoneNumber) {
        
        List<TransactionHistoryResponseDTO> transactions = 
        transactionHistoryService.getLastFiveTransactions(phoneNumber);
        
        return ResponseEntity.ok(transactions);
    }
}
