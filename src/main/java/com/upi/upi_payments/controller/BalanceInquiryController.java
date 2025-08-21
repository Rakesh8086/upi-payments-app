package com.upi.upi_payments.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upi.upi_payments.dto.BalanceResponseDTO;
import com.upi.upi_payments.service.BalanceInquiryService;

@RestController
@RequestMapping("/api/wallets")
public class BalanceInquiryController {

    @Autowired
    private BalanceInquiryService balanceInquiryService;

    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable String phoneNumber) {
        
        BigDecimal balance = balanceInquiryService.getBalance(phoneNumber);
        
        BalanceResponseDTO responseDTO = new BalanceResponseDTO(phoneNumber, balance);
        
        return ResponseEntity.ok(responseDTO);
    }
}
