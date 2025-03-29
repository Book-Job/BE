package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends BaseException {
    private final static String TOKEN_UNAUTHORIZED = "토큰 인증에 실패했습니다.";
    private final static String DEACTIVATED = "삭제되었거나 정지 상태인 계정입니다.";

    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public static UnAuthorizedException securityUnauthorized() {
        return new UnAuthorizedException(TOKEN_UNAUTHORIZED);
    }

    public static UnAuthorizedException deactivatedMemberUnauthorized() {
        return new UnAuthorizedException(DEACTIVATED);
    }
}
