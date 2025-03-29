package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    static private final String DUPLICATED_EMAIL = "이미 가입된 회원입니다. 이메일: %s";

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public static InternalServerError duplicatedEmail(String email) {
        String formattedMessage = String.format(DUPLICATED_EMAIL, email);
        return new InternalServerError(formattedMessage);
    }

}
