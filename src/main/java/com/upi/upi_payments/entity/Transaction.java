package com.upi.upi_payments.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

// A foreign key is a column or set of columns in a database table 
// that refers to the primary key of another table

// @SuppressWarnings("unused") can comment it finally once all variables are used
// Use this just for convenience of not seeing warning signals

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_wallet_id", referencedColumnName = "id")
    private Wallet senderWallet;

    @ManyToOne
    @JoinColumn(name = "receiver_wallet_id", referencedColumnName = "id")
    private Wallet receiverWallet;

    private BigDecimal transferAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public Transaction(){

    }

    public Transaction(Long id, Wallet sender, Wallet receiver, BigDecimal amount,
    Status status, LocalDateTime createdTime, String remark, User user){
        this.id = id;
        this.senderWallet = sender;
        this.receiverWallet = receiver;
        this.transferAmount = amount;
        this.status = status;
        this.createdAt = createdTime;
        this.remarks = remark;
    }

    public void setId(Long Id){
        id = Id;
    }
    public Long getId(){
        return id;
    }

    public void setSenderWallet(Wallet sender){
        this.senderWallet = sender;
    }
    public Wallet getSenderWallet(){
        return senderWallet;
    }

    public void setReceiverWallet(Wallet receiver){
        this.receiverWallet = receiver;
    }
    public Wallet getReceiverWallet(){
        return receiverWallet;
    }

    public void setTransferAmount(BigDecimal amount){
        this.transferAmount = amount;
    }
    public BigDecimal getTransferAmount(){
        return transferAmount;
    }

    public void setStatus(Status status){
        this.status = status;
    }
    public Status getStatus(){
        return status;
    }

    public void setCreatedAt(LocalDateTime time){
        this.createdAt = time;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setRemarks(String remark){
        this.remarks = remark;
    }
    public String getRemarks(){
        return remarks;
    }

    public void setTransactionType(TransactionType type){
        this.transactionType = type;
    }
    public TransactionType getTransactionType(){
        return transactionType;
    }
};
