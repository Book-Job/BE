package com.bookjob.bookmark.dto.response;

import java.time.LocalDateTime;

public record MyBookMarksResponse (
        Long bookMarkId,
        Long entityId,
        String title,
        int viewCount,
        String recruitmentCategory,
        String employmentType,
        LocalDateTime createdAt
) {
}
