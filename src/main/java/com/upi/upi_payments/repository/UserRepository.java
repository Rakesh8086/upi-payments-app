package com.upi.upi_payments.repository;

import com.upi.upi_payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by their phone number.
    Optional<User> findByPhoneNumber(String phoneNumber);
    
    Optional<User> findByUserProvidedKey(String userProvidedKey);
}
