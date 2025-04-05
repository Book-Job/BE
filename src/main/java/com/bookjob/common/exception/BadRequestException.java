package com.bookjob.common.exception;

import com.bookjob.job.domain.EmploymentType;
import com.bookjob.job.domain.JobCategory;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BadRequestException extends BaseException {
    static private final String VERIFICATION_CODE_EXPIRED = "인증 시간이 초과되었습니다.";
    static private final String INVALID_VERIFICATION_CODE = "인증 번호가 일치하지 않습니다.";
    static private final String INVALID_JOB_CATEGORY = "유효하지 않은 직무 카테고리입니다.: ";
    static private final String INVALID_EMPLOYMENT_TYPE = "유효하지 않은 고용 형태입니다.: ";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException verificationCodeExpired() {
        return new BadRequestException(VERIFICATION_CODE_EXPIRED);
    }

    public static BadRequestException invalidVerificationCode() {
        return new BadRequestException(INVALID_VERIFICATION_CODE);
    }

    public static BadRequestException invalidJobCategory(String jobCategory) {
        String validValues = Arrays.stream(JobCategory.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = String.format(INVALID_JOB_CATEGORY + "{%s}, 유효한 값: {%s}", jobCategory, validValues);
        return new BadRequestException(message);
    }

    public static BadRequestException invalidEmployType(String employType) {
        String validValues = Arrays.stream(EmploymentType.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        String message = String.format(INVALID_EMPLOYMENT_TYPE + "{%s}, 유효한 값: {%s}", employType, validValues);
        return new BadRequestException(message);
    }
}
