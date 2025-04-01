package com.bookjob.comment.dto.request;

public record CommentRequest(
        String content,
        String nickname,
        String password
) {
}
