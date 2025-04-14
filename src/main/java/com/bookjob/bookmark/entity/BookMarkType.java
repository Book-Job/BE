package com.bookjob.bookmark.entity;

import com.bookjob.common.exception.BadRequestException;

public enum BookMarkType {
    JOB_POSTING,
    JOB_SEEKING;

    public static BookMarkType fromString(String value) {
        try {
            return BookMarkType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw BadRequestException.invalidEnumValue(value, BookMarkType.class);
        }
    }
}
