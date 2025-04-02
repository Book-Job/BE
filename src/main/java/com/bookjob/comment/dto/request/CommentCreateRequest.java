package com.bookjob.comment.dto.request;

public record CommentCreateRequest(
        String content,
        String nickname,
        String password
) {
}
