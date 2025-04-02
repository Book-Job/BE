package com.bookjob.email.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailReason reason;

    public EmailVerification(String toEmail, String code, LocalDateTime expirationTime, EmailReason reason) {
        this.email = toEmail;
        this.code = code;
        this.expirationTime = expirationTime;
        this.reason = reason;
    }

    public void update(String code, LocalDateTime expirationTime) {
        this.code = code;
        this.expirationTime = expirationTime;
    }
}
