package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.UserType;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.UserRepository;
// import com.upi.upi_payments.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
// Every single step of the process has to saved in DB
// After updation, the updated state of the entity has to set using SET method
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String phoneNumber, UserType userType){
        if(userRepository.findByPhoneNumber(phoneNumber).isPresent()){
            throw new RuntimeException("Phone number already registered");
        }
        
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setUserType(userType);
        user.setCreatedTime(LocalDateTime.now());

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setUpdatedAt(LocalDateTime.now());

        // Bi-directional linking
        wallet.setUser(user);
        user.setWallet(wallet);

        // Save only user, wallet will be saved automatically due to cascade
        return userRepository.save(user);
    }
}