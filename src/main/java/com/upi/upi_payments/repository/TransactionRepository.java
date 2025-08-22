package com.upi.upi_payments.repository;

import com.upi.upi_payments.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findFirst5BySenderWallet_User_PhoneNumberOrReceiverWallet_User_PhoneNumberOrderByCreatedAtDesc(
        String senderPhoneNumber, 
        String receiverPhoneNumber
    );
    // should follow convention while naming this function. Not as per wish
}
