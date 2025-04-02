package com.bookjob.board.dto.response;

import java.time.LocalDateTime;

public record BoardPreviewResponse(
        Long boardId,
        String title,
        String text,
        String nickname,
        long viewCount,
        boolean isAuthentic,
        long commentCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
