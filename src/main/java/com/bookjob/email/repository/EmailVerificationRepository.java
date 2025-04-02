package com.bookjob.email.repository;

import com.bookjob.email.domain.EmailReason;
import com.bookjob.email.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    void deleteByEmail(String email);

    Optional<EmailVerification> findByEmailAndReason(String toEmail, EmailReason register);

    Optional<EmailVerification> findByEmailAndCodeAndReason(String email, String code, EmailReason reason);
}
