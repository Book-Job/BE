package com.bookjob.email.service;

import com.bookjob.common.exception.BadRequestException;
import com.bookjob.common.exception.InternalServerError;
import com.bookjob.email.domain.EmailBuilder;
import com.bookjob.email.domain.EmailVerification;
import com.bookjob.email.dto.EmailVerificationRequest;
import com.bookjob.email.repository.EmailVerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Transactional
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender emailSender;

    private static final int CODE_LENGTH = 6;
    private static final long EXPIRATION_MINUTES = 5;
    private static final String EMAIL_TITLE = "[북잡] 이메일 인증 번호를 알려드립니다.";

    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

    public void requestEmailVerification(String toEmail) {
        // 1. 인증 번호 생성
        String code = createCode();

        // 2. 이메일 작성 및 전송
        sendEmail(FROM_EMAIL, toEmail, EMAIL_TITLE, EmailBuilder.buildEmail(code));

        // 3. 코드 저장 및 업데이트
        saveOrUpdateVerification(toEmail, code);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < CODE_LENGTH; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw InternalServerError.generateRandomNumberError();
        }
    }

    public void sendEmail(String sendEmail, String toEmail, String title, String content) {

        // HTML 형식의 이메일 전송하기 위한 설정
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(sendEmail);
            helper.setTo(toEmail);
            helper.setSubject(title);
            // 이메일 형식을 html로 해석되야 함을 의미. false로 설정할 경우 단순 text로 전송됨
            helper.setText(content, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw InternalServerError.emailSendError(toEmail);
        }
    }

    private void saveOrUpdateVerification(String toEmail, String code) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        // 이미 인증 메일을 보낸 사용자라면 인증 코드와 만료 시간 업데이트, 아니라면 새로 생성 후 저장
        EmailVerification verification = emailVerificationRepository.findByEmail(toEmail)
                .orElse(new EmailVerification(toEmail, code, expirationTime));

        verification.update(code, expirationTime);
        emailVerificationRepository.save(verification);
    }

    public void verifyCode(EmailVerificationRequest request) {
        String email = request.email();
        String code = request.code();
        Optional<EmailVerification> verification = emailVerificationRepository.findByEmailAndCode(email, code);

        if (verification.isEmpty()) {
            throw BadRequestException.invalidVerificationCode();
        }

        LocalDateTime now = LocalDateTime.now();
        // 현재시간보다 만료시간이 뒤인 경우(만료시간 아직 안 지남), 인증 메일 지우고 완료 메시지 전송
        if (verification.get().getExpirationTime().isAfter(now)) {
            emailVerificationRepository.deleteByEmail(email);
        } else {
            throw BadRequestException.verificationCodeExpired();
        }
    }
}
