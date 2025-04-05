package com.bookjob.member.dto;

import java.time.LocalDateTime;

public record MyPostingsInBoard (
        Long boardId,
        String title,
        LocalDateTime createdAt,
        long commentCount,
        long viewCount
) {
}
