package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    static private final String DUPLICATED_EMAIL = "이미 가입된 회원입니다. 이메일: %s";
    static private final String DUPLICATED_LOGINID = "중복된 아이디입니다.";
    static private final String DUPLICATED_NICKNAME = "중복된 닉네임입니다.";

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public static ConflictException duplicatedEmail(String email) {
        String formattedMessage = String.format(DUPLICATED_EMAIL, email);
        return new ConflictException(formattedMessage);
    }

    public static ConflictException duplicatedLoginId() {
        return new ConflictException(DUPLICATED_LOGINID);
    }

    public static ConflictException duplicatedNickname() {
        return new ConflictException(DUPLICATED_NICKNAME);
    }
}
