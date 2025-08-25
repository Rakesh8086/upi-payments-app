package com.upi.upi_payments.service;

import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // Find the user by their phone number using the UserRepository.
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "User not found with phone number: " + phoneNumber
        ));

        // The User entity already implements UserDetails, so we can return it directly.
        return user;
    }
}
