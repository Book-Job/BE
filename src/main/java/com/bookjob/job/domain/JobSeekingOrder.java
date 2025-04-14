package com.bookjob.job.domain;

import com.bookjob.common.exception.BadRequestException;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum JobSeekingOrder {
    LATEST,
    EDITOR_LATEST,
    DESIGNER_LATEST,
    MARKETER_LATEST,
    ILLUSTRATOR_LATEST,
    SUPPORT_LATEST,
    ETC_LATEST;

    public static JobSeekingOrder fromString(String value) {
        if (value == null) {
            return JobSeekingOrder.LATEST;
        }
        try {
            return JobSeekingOrder.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validValues = Arrays.stream(JobPostingOrder.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException("유효하지 않은 정렬 조건입니다: " + value +
                    ". 유효한 값: [" + validValues + "]");
        }
    }
}
