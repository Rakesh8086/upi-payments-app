package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.entity.TransactionType;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.entity.Status;
import com.upi.upi_payments.repository.WalletRepository;

import jakarta.transaction.Transactional;

import com.upi.upi_payments.repository.TransactionRepository;
import com.upi.upi_payments.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
// Every single step of the process has to saved in DB
// After updation, the updated state of the entity has to set using SET method
public class TransferService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction performTransaction(String senderPhoneNumber, String receiverPhoneNumber, 
    BigDecimal amount){
        User sender = userRepository.findByPhoneNumber(senderPhoneNumber)
            .orElseThrow(() -> 
                new IllegalArgumentException("Sender not found with phone number: " + senderPhoneNumber));

        User receiver = userRepository.findByPhoneNumber(receiverPhoneNumber)
            .orElseThrow(() -> 
                new IllegalArgumentException("Receiver not found with phone number: " + receiverPhoneNumber));
        
        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        Transaction transaction = new Transaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setTransferAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.TRANSFER);

        // Check balance
        if(senderWallet.getBalance().compareTo(amount) < 0){
            transaction.setStatus(Status.FAILED);
            return transactionRepository.save(transaction); // save failure record
        }

        // Update balances
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));
        senderWallet.setUpdatedAt(LocalDateTime.now());
        receiverWallet.setUpdatedAt(LocalDateTime.now());

        // Persist changes
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // Mark transaction success
        transaction.setStatus(Status.SUCCESS);
        return transactionRepository.save(transaction);
    }
}