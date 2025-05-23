package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BadRequestException extends BaseException {
    static private final String VERIFICATION_CODE_EXPIRED = "인증 시간이 초과되었습니다.";
    static private final String INVALID_VERIFICATION_CODE = "인증 번호가 일치하지 않습니다.";
    static private final String INVALID_JOB_CATEGORY = "구인/구직 중 어느 카테고리에도 속하지 않습니다. : {%s}";
    static private final String INVALID_ENUM_VALUE = "유효하지 않은 값입니다.: {%s}, 유효한 값: {%s}";
    static private final String JOB_POSTING_ALREADY_DELETED = "이미 삭제된 구인 글입니다.";
    static private final String JOB_SEEKING_ALREADY_DELETED = "이미 삭제된 구직 글입니다.";
    static private final String ALREADY_DELETED = "이미 삭제된 항목입니다.";
    static private final String PASSWORD_MISMATCH = "비밀번호가 일치하지 않습니다.";
    static private final String INVALID_RESET_TOKEN = "유효하지 않은 임시 토큰입니다.";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException verificationCodeExpired() {
        return new BadRequestException(VERIFICATION_CODE_EXPIRED);
    }

    public static BadRequestException invalidVerificationCode() {
        return new BadRequestException(INVALID_VERIFICATION_CODE);
    }

    public static BadRequestException invalidJobCategory(String category) {
        String message = String.format(INVALID_JOB_CATEGORY, category);
        return new BadRequestException(message);
    }

    public static <T extends Enum<T>> BadRequestException invalidEnumValue(String value, Class<T> enumClass) {
        String validValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = String.format(INVALID_ENUM_VALUE, value, validValues);
        return new BadRequestException(message);
    }

    public static BadRequestException JobPostingAlreadyDeleted() {
        return new BadRequestException(JOB_POSTING_ALREADY_DELETED);
    }

    public static BadRequestException JobSeekingAlreadyDeleted() {
        return new BadRequestException(JOB_SEEKING_ALREADY_DELETED);
    }

    public static BadRequestException alreadyDeleted() {
        return new BadRequestException(ALREADY_DELETED);
    }

    public static BadRequestException passwordMismatch() {
        return new BadRequestException(PASSWORD_MISMATCH);
    }

    public static BadRequestException invalidResetToken() {
        return new BadRequestException(INVALID_RESET_TOKEN);
    }
}
