package com.upi.upi_payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.upi.upi_payments.repository.UserRepository;
import com.upi.upi_payments.repository.WalletRepository;
import com.upi.upi_payments.repository.TransactionRepository;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.entity.TransactionType;
import com.upi.upi_payments.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction deposit(BigDecimal amount){
        // Retrieve the authenticated user's phone number from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String phoneNumber = userDetails.getUsername();

        User user = userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> 
                new IllegalArgumentException("User not found with phone number: " + phoneNumber));

        Wallet wallet = user.getWallet();

        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setSenderWallet(null);
        transaction.setReceiverWallet(wallet);
        transaction.setTransferAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(Status.SUCCESS);

        return transactionRepository.save(transaction);
    }
}
