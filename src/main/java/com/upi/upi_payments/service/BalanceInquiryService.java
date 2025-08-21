package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.UserRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceInquiryService {
    
    @Autowired
    private UserRepository userRepository;

    public BigDecimal getBalance(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> 
                new IllegalArgumentException("User not found with phone number: " + phoneNumber));

        Wallet wallet = user.getWallet();

        return wallet.getBalance();
    }
}