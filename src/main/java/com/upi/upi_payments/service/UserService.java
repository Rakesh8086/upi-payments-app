package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.UserType;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.UserRepository;
import com.upi.upi_payments.repository.WalletRepository;
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
    @Autowired
    private WalletRepository walletRepository;

    public User registerUser(String name, String phoneNumber, UserType userType){
        // new user object
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setUserType(userType);
        user.setCreatedTime(LocalDateTime.now());

        // save user to DB
        user = userRepository.save(user);

        // create Wallet object
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setUpdatedTime(LocalDateTime.now());
        wallet.setUser(user);

        // save wallet
        walletRepository.save(wallet);

        // update user with wallet reference
        user.setWallet(wallet);
        return userRepository.save(user);
    }
}