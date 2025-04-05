package com.bookjob.job.domain;

import com.bookjob.common.exception.BadRequestException;

public enum EmploymentType {
    FULL_TIME, TEMPORARY, FREELANCE, INTERN;

    public static EmploymentType fromString(String value) {
        try {
            return EmploymentType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw BadRequestException.invalidEmployType(value);
        }
    }
}
