package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    static final String FORBIDDEN = "접근 권한이 없습니다.";

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public static ForbiddenException forbidden() {
        return new ForbiddenException(FORBIDDEN);
    }
}
