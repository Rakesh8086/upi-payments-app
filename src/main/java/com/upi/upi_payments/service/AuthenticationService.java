package com.upi.upi_payments.service;

import com.upi.upi_payments.dto.LoginRequestDTO;
import com.upi.upi_payments.dto.UserRegistrationDTO;
import com.upi.upi_payments.entity.User;
import com.upi.upi_payments.entity.Wallet;
import com.upi.upi_payments.repository.UserRepository;
import com.upi.upi_payments.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user with a wallet and saves them to the database.
     * @param userRegistrationDTO The DTO containing the user registration details.
     */
    public void register(UserRegistrationDTO userRegistrationDTO) {
        // Check if a user with the given phone number already exists to avoid duplicates
        if (userRepository.findByPhoneNumber(userRegistrationDTO.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }

        // Create a new User entity from the DTO
        User user = new User();
        user.setUserName(userRegistrationDTO.getUserName());
        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword())); // Encode the password
        user.setUserType(userRegistrationDTO.getUserType());
        user.setCreatedAt(LocalDateTime.now());

        // Create a new wallet for the user with an initial balance of 0
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUpdatedAt(LocalDateTime.now());
        wallet.setUser(user); // Link the wallet to the user

        // Link the user to the wallet
        user.setWallet(wallet);

        // Save the user (which will also save the wallet due to CascadeType.ALL)
        userRepository.save(user);
    }

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     * @param loginRequestDTO The DTO containing the login credentials.
     * @return The JWT token string.
     */
    public String login(LoginRequestDTO loginRequestDTO) {
        // Authenticate the user using the AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getPhoneNumber(),
                        loginRequestDTO.getPassword()
                )
        );

        // If authentication succeeds, load the user details to generate a token
        UserDetails userDetails = userRepository.findByPhoneNumber(loginRequestDTO.getPhoneNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate and return the JWT token
        return jwtService.generateToken(userDetails);
    }
}
