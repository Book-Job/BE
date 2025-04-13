package com.bookjob.job.dto.request;

import java.time.LocalDateTime;

public record JobPostingUpdateRequest(
        String title,
        String text,
        String jobCategory,
        String employmentType,
        String websiteUrl,
        String location,
        int experienceMin,
        int experienceMax,
        LocalDateTime closingDate
) {
}
