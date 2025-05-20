package com.bookjob.auth.service;

import com.bookjob.auth.domain.entity.TemporaryToken;
import com.bookjob.auth.domain.repository.TemporaryTokenRepository;
import com.bookjob.common.exception.BadRequestException;
import com.bookjob.common.exception.ConflictException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.email.dto.EmailVerificationRequest;
import com.bookjob.member.dto.request.ChangePasswordRequest;
import com.bookjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;

    public void checkDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw ConflictException.duplicatedEmail(email);
        }
    }

    public void checkDuplicatedLoginId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw ConflictException.duplicatedLoginId();
        }
    }

    public void checkDuplicatedNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw ConflictException.duplicatedNickname();
        }
    }

    public void doesEmailExist(String email) {
        if (!memberRepository.existsByEmail(email)) {
            throw NotFoundException.emailNotFound();
        }
    }

    public String createTokenForChangePassword(String email) {
        String resetToken = UUID.randomUUID().toString();
        TemporaryToken token = new TemporaryToken(resetToken, email, LocalDateTime.now().plusMinutes(15));
        temporaryTokenRepository.save(token);
        return resetToken;
    }

    public String checkResetToken(String resetToken) {
        TemporaryToken temporaryToken =  temporaryTokenRepository.findByToken(resetToken).orElseThrow(BadRequestException::invalidResetToken);
        return temporaryToken.getEmail();
    }
}
