package com.bookjob.job.dto.response;

import java.time.LocalDateTime;
public record JobSeekingPreviewResponse (
        long id,
        String nickname,
        String title,
        String text,
        int viewCount,
        String experience,
        String employmentType,
        String jobCategory,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
