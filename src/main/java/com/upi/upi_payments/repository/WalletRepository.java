package com.upi.upi_payments.repository;

import com.upi.upi_payments.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;

@Repository
// generates all the standard queries for this entity, whose primary key is of type Long
public interface WalletRepository extends JpaRepository<Wallet, Long> {

};
