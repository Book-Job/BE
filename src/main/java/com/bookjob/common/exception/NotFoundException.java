package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    static private final String EMAIL_NOT_FOUND = "이메일을 찾을 수 없습니다. ";
    static private final String BOARD_NOT_FOUND = "게시글을 찾을 수 없습니다. ";
    static private final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public static NotFoundException emailNotFound() {
        return new NotFoundException(EMAIL_NOT_FOUND);
    }

    public static NotFoundException boardNotFound() {
        return new NotFoundException(BOARD_NOT_FOUND);
    }

    public static NotFoundException commentNotFound() {
        return new NotFoundException(COMMENT_NOT_FOUND);
    }
}
