package com.bookjob.auth.controller;

import com.bookjob.auth.facade.AuthFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.email.dto.EmailRequest;
import com.bookjob.email.dto.EmailVerificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/emails")
    public ResponseEntity<CommonResponse<String>> sendCodeToEmail(@Valid @RequestBody EmailRequest request) {
        authFacade.sendCodeToEmail(request.email());
        return ResponseEntity.ok(CommonResponse.success());
    }

    @PostMapping("/emails/code")
    public ResponseEntity<CommonResponse<Void>> verificationEmail(@Valid @RequestBody EmailVerificationRequest request) {
        authFacade.verifyCode(request);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
