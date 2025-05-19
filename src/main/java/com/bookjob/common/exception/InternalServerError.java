package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends BaseException {
    static private final String EMAIL_SEND_ERROR = "이메일을 전송하던 중 문제가 발생했습니다. 이메일: %s";
    static private final String GENERATE_RANDOM_NUMBER_ERROR = "인증번호를 만들던 중 오류가 발생했습니다.";
    static private final String NEGATIVE_COUNT_ERROR = "카운트 값이 음수가 될 수 없습니다. 요청된 값: %d";

    public InternalServerError(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static InternalServerError emailSendError(String email) {
        String formattedMessage = String.format(EMAIL_SEND_ERROR, email);
        return new InternalServerError(formattedMessage);
    }

    public static InternalServerError generateRandomNumberError() {
        return new InternalServerError(GENERATE_RANDOM_NUMBER_ERROR);
    }

    public static InternalServerError negativeCountError(Long count) {
        String formattedMessage = String.format(NEGATIVE_COUNT_ERROR, count);
        return new InternalServerError(formattedMessage);
    }
}
