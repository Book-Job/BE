package com.bookjob.board.dto.response;

import java.time.LocalDateTime;

public record BoardDetailResponse(
        String title,
        String text,
        String nickname,
        long commentCount,
        long viewCount,
        boolean isAuthentic,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}


