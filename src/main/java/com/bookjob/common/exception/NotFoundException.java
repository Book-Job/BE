package com.bookjob.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    static private final String EMAIL_NOT_FOUND = "이메일을 찾을 수 없습니다. ";
    static private final String BOARD_NOT_FOUND = "게시글을 찾을 수 없습니다. 게시글 Id: %s";
    static private final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";
    static private final String LOGINID_NOT_FOUND = "로그인 아이디를 찾을 수 없습니다.";
    static private final String MEMBER_NOT_FOUND = "회원을 찾을 수 없습니다.";
    static private final String JOB_POSTING_NOT_FOUND = "구인 글을 찾을 수 없습니다.";
    static private final String JOB_SEEKING_NOT_FOUND = "구직 글을 찾을 수 없습니다.";
    static private final String BOOKMARK_NOT_FOUND = "스크랩을 찾을 수 없습니다.";
    static private final String BOOKMARK_TARGET_NOT_FOUND = "스크랩 대상을 찾을 수 없습니다.";

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public static NotFoundException emailNotFound() {
        return new NotFoundException(EMAIL_NOT_FOUND);
    }

    public static NotFoundException boardNotFound(Long boardId) {
        String formattedMessage = String.format(BOARD_NOT_FOUND, boardId);
        return new NotFoundException(formattedMessage);
    }

    public static NotFoundException commentNotFound() {
        return new NotFoundException(COMMENT_NOT_FOUND);
    }

    public static NotFoundException loginIdNotFound() {
        return new NotFoundException(LOGINID_NOT_FOUND);
    }

    public static NotFoundException memberNotFound() {
        return new NotFoundException(MEMBER_NOT_FOUND);
    }

    public static NotFoundException jobPostingNotFound() {
        return new NotFoundException(JOB_POSTING_NOT_FOUND);
    }

    public static NotFoundException jobSeekingNotFound() {
        return new NotFoundException(JOB_SEEKING_NOT_FOUND);
    }

    public static NotFoundException bookMarkNotFound() {
        return new NotFoundException(BOOKMARK_NOT_FOUND);
    }

    public static NotFoundException bookMarkTargetNotFound() {
        return new NotFoundException(BOOKMARK_TARGET_NOT_FOUND);
    }
}
