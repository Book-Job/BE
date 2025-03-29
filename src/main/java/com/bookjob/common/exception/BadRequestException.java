package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    static private final String VERIFICATION_CODE_EXPIRED = "인증 시간이 초과되었습니다.";
    static private final String INVALID_VERIFICATION_CODE = "인증 번호가 일치하지 않습니다.";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException verificationCodeExpired() {
        return new BadRequestException(VERIFICATION_CODE_EXPIRED);
    }

    public static BadRequestException invalidVerificationCode() {
        return new BadRequestException(INVALID_VERIFICATION_CODE);
    }

}
