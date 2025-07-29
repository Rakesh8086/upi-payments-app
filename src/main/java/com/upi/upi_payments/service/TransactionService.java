package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.Transaction;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.entity.Status;
import com.upi.upi_payments.repository.WalletRepository;
import com.upi.upi_payments.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
// Every single step of the process has to saved in DB
// After updation, the updated state of the entity has to set using SET method
public class TransactionService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction makeTransaction(Wallet sender, Wallet receiver, 
    BigDecimal amount, String remarks){
        Transaction transaction = new Transaction();
        transaction.setSenderWallet(sender);
        transaction.setReceiverWallet(receiver);
        transaction.setTransferAmount(amount);
        transaction.setCreatedTime(LocalDateTime.now());
        transaction.setRemarks(remarks);

        if(sender.getBalance().compareTo(amount) >= 0){
            // Update balances
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
            sender.setUpdatedTime(LocalDateTime.now());
            receiver.setUpdatedTime(LocalDateTime.now());

            // Save updated wallets
            walletRepository.save(sender);
            walletRepository.save(receiver);

            transaction.setStatus(Status.SUCCESS);
        } 
        else{
            transaction.setStatus(Status.FAILED);
        }

        // save transaction record
        return transactionRepository.save(transaction);
    }
}