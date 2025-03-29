package com.bookjob.auth.controller;

import com.bookjob.auth.facade.AuthFacade;
import com.bookjob.common.dto.CommonResponse;
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

    @PostMapping("/emails")
    public ResponseEntity<?> sendCodeToEmail(@Valid @RequestBody EmailRequest request) {
        authFacade.sendCodeToEmail(request.email());
        return ResponseEntity.ok(CommonResponse.success());
    }

    @PostMapping("/emails/code")
    public ResponseEntity<?> verificationEmail(@Valid @RequestBody EmailVerificationRequest request) {
        authFacade.verifyCode(request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/check-id")
    public ResponseEntity<?> checkDuplicatedLoginId (
            @RequestParam(name = "loginId") @NotBlank(message = "아이디는 필수입니다.") String loginId) {
        authFacade.checkDuplicatedLoginId(loginId);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
