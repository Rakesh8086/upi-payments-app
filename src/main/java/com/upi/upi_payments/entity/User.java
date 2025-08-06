package com.upi.upi_payments.entity;
import java.math.BigDecimal; 
import java.time.LocalDateTime;
import jakarta.persistence.*;
/* jakarta.persistence package provides a set of annotations 
 that are used to define entities, map them to database tables, 
 specify relationships, and configure persistence behavior. Ex: @Entity, @Table*/ 

@SuppressWarnings("unused")
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet; // wallet is a another entity

    public User(){
                
    }

    public User(long id, String userName, String phoneNumber, UserType userType, 
    LocalDateTime createdAt, Wallet wallet){ 
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.createdAt = createdAt;
        this.wallet = wallet;
    }

    public void setId(Long Id){
        id = Id;
    }
    public Long getId(){
        return id;
    }

    public void setName(String name){
        if(!name.isEmpty()){
            userName = name;
        }
        else{
            System.out.println("Username should not be empty");
        }
    }
    public String getName(){
        return userName;
    }

    public void setPhoneNumber(String number){
        if(number.length() == 10){
            phoneNumber = number;
        }
        else{
            System.out.println("Phone number must be 10 digits exactly");
        }
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }

    public UserType getUserType(){
        return userType;
    }
    public void setUserType(UserType userType){
        this.userType = userType;
    }

    public void setCreatedTime(LocalDateTime time){
        createdAt = time;
    }
    public LocalDateTime getCreatedTime(){
        return createdAt;
    }

    public Wallet getWallet(){
        return wallet;
    }
    public void setWallet(Wallet wallet){
        this.wallet = wallet;
    }
};
