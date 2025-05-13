package com.bookjob.comment.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String text,
        String nickname,
        boolean isAuthentic,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        boolean isWriter
) {
}
