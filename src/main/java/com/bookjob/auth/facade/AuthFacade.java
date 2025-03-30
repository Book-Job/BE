package com.bookjob.auth.facade;

import com.bookjob.auth.service.AuthService;
import com.bookjob.email.dto.EmailVerificationRequest;
import com.bookjob.email.service.EmailService;
import com.bookjob.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;
    private final EmailService emailService;
    private final MemberReadService memberReadService;

    public void sendCodeToEmail(String email) {
        authService.checkDuplicatedEmail(email);
        emailService.requestEmailVerification(email);
    }

    public void verifyCode(EmailVerificationRequest request) {
        emailService.verifyCode(request);
    }

    public void checkDuplicatedLoginId(String loginId) {
        authService.checkDuplicatedLoginId(loginId);
    }

    public void checkDuplicatedNickname(String nickname) {
        authService.checkDuplicatedNickname(nickname);
    }

    public void sendCodeToEmailForLoginId(String email) {
        authService.doesEmailExist(email);
        emailService.requestEmailVerification(email);
    }

    public String verifyCodeForLoginId(EmailVerificationRequest request) {
        emailService.verifyCode(request);
        return memberReadService.getMaskedLoginId(request.email());
    }
}
