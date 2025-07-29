package com.upi.upi_payments.repository;

import com.upi.upi_payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Only Write Custom Queries when The field is not the primary key,
    // need complex conditions to filter 
    Optional<User> findByPhoneNumber(String phoneNumber);
}
