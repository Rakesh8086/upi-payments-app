package com.upi.upi_payments.entity;
import java.math.BigDecimal; 
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* jakarta.persistence package provides a set of annotations 
 that are used to define entities, map them to database tables, 
 specify relationships, and configure persistence behavior. Ex: @Entity, @Table*/ 

@SuppressWarnings("unused")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
};
