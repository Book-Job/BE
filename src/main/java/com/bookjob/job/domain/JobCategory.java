package com.bookjob.job.domain;

import com.bookjob.common.exception.BadRequestException;

public enum JobCategory {
    EDITOR, DESIGNER, MARKETER, ILLUSTRATOR, SUPPORT, ETC;

    public static JobCategory fromString(String value) {
        try {
            return JobCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw BadRequestException.invalidEnumValue(value, JobCategory.class);
        }
    }
}
