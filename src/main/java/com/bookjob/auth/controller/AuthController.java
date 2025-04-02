package com.bookjob.auth.controller;

import com.bookjob.auth.dto.FindLoginIdResponse;
import com.bookjob.auth.facade.AuthFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.email.domain.EmailReason;
import com.bookjob.email.dto.EmailRequest;
import com.bookjob.email.dto.EmailVerificationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    /**
     * 회원 가입 시 이메일 인증 번호 요청
     */
    @PostMapping("/emails")
    public ResponseEntity<?> sendCodeToEmail(@Valid @RequestBody EmailRequest request) {
        authFacade.sendCodeToEmail(request.email());
        return ResponseEntity.ok(CommonResponse.success());
    }

    /**
     * 회원 가입 시 인증 번호 입력 후 확인 요청
     */
    @PostMapping("/emails/code")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody EmailVerificationRequest request) {
        authFacade.verifyCode(request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/check-id")
    public ResponseEntity<?> checkDuplicatedLoginId(
            @RequestParam(name = "loginId") @NotBlank(message = "아이디는 필수 입력값입니다.") String loginId) {
        authFacade.checkDuplicatedLoginId(loginId);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkDuplicatedNickname(
            @RequestParam(name = "nickname") @NotBlank(message = "닉네임은 필수 입력값입니다.") String nickname) {
        authFacade.checkDuplicatedNickname(nickname);
        return ResponseEntity.ok(CommonResponse.success());
    }

    /**
     * 아이디 찾기 시, 이메일 인증 요청
     */
    @PostMapping("/email-verification/id")
    public ResponseEntity<?> sendCodeToEmailForLoginId(@Valid @RequestBody EmailRequest request) {
        authFacade.sendCodeToEmailForLoginId(request.email());
        return ResponseEntity.ok(CommonResponse.success());
    }

    /**
     * 아이디 찾기 시 인증 번호 입력 후 확인 요청
     */
    @PostMapping("/email-verification/id/code")
    public ResponseEntity<?> verifyCodeForLoginId(@Valid @RequestBody EmailVerificationRequest request) {
        String maskedLoginId = authFacade.verifyCodeForLoginId(request);
        return ResponseEntity.ok(CommonResponse.success(new FindLoginIdResponse(maskedLoginId)));
    }
}
