package com.bookjob.board.dto.response;

import java.time.LocalDateTime;

public record BoardDetailResponse(
        String title,
        String text,
        String nickname,
        long commentCount,
        long viewCount,
        boolean isAuthentic,
        boolean isWriter,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}


