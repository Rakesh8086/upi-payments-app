package com.upi.upi_payments.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

// @SuppressWarnings("unused") commenting out does not make any difference
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    private LocalDateTime updatedAt;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // user Id is the foreign key in wallets table

    public Wallet(){

    }

    public Wallet(Long id, BigDecimal balance, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.balance = balance;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public void setBalance(BigDecimal initialBalance){
        if(initialBalance.compareTo(BigDecimal.ZERO) >= 0){
            this.balance = initialBalance;
        } 
        else{
            System.out.println("Initial Balance cannot be less than 0.");
        }
    }

    public LocalDateTime getUpdatedTime(){
        return updatedAt;
    }

    public void setUpdatedTime(LocalDateTime time){
        updatedAt = time;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
};
