package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BadRequestException extends BaseException {
    static private final String VERIFICATION_CODE_EXPIRED = "인증 시간이 초과되었습니다.";
    static private final String INVALID_VERIFICATION_CODE = "인증 번호가 일치하지 않습니다.";
    static private final String INVALID_ENUM_VALUE = "유효하지 않은 값입니다.: {%s}, 유효한 값: {%s}";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException verificationCodeExpired() {
        return new BadRequestException(VERIFICATION_CODE_EXPIRED);
    }

    public static BadRequestException invalidVerificationCode() {
        return new BadRequestException(INVALID_VERIFICATION_CODE);
    }

    public static <T extends Enum<T>> BadRequestException invalidEnumValue(String value, Class<T> enumClass) {
        String validValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = String.format(INVALID_ENUM_VALUE, value, validValues);
        return new BadRequestException(message);
    }
}
