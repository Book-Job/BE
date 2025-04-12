package com.bookjob.job.dto.response;

import java.time.LocalDateTime;

public record JobPostingPreviewResponse(
        long id,
        String nickname,
        String title,
        String text,
        int viewCount,
        String websiteUrl,
        int experienceMin,
        int experienceMax,
        LocalDateTime closingDate,
        String employmentType,
        String jobCategory,
        String location,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
