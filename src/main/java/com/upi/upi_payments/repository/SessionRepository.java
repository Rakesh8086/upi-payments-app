package com.upi.upi_payments.repository;

import com.upi.upi_payments.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    // This method will be used to find a session by its token.
    Optional<Session> findByToken(String token);
    
}