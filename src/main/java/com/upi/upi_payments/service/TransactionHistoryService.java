package com.upi.upi_payments.service;

import com.upi.upi_payments.dto.TransactionHistoryResponseDTO;
import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.repository.TransactionRepository;
import com.upi.upi_payments.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TransactionHistoryResponseDTO> getLastFiveTransactions(String phoneNumber) {
        
        userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new IllegalArgumentException("User not found with phone number: " + phoneNumber));

        List<Transaction> transactions = 
            transactionRepository.findFirst5BySenderWallet_User_PhoneNumberOrReceiverWallet_User_PhoneNumberOrderByCreatedAtDesc(
                phoneNumber, phoneNumber);

        // Mapping the Transaction entities to the DTOs
        return transactions.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Helper method to convert a Transaction entity to a DTO
    private TransactionHistoryResponseDTO convertToDto(Transaction transaction) {
        return new TransactionHistoryResponseDTO(
            transaction.getId(),
            transaction.getTransactionType(),
            transaction.getTransferAmount(),
            transaction.getCreatedAt(),
            transaction.getStatus(),
            transaction.getRemarks()
        );
    }
}
