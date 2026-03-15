package com.example.MIS.and.Invoicing.System.userregistration.login.repository;

import com.example.MIS.and.Invoicing.System.userregistration.login.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifiactionTokenRespository extends JpaRepository<EmailVerificationToken,Integer> {
    Optional<EmailVerificationToken> findByToken(String token);
}
