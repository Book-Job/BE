package com.bookjob.job.dto.request;

import java.time.LocalDateTime;

public record JobPostingCreateRequest(
        String title,
        String text,
        String jobCategory,
        String employmentType,
        String writer,
        String websiteUrl,
        String location,
        int experienceMin,
        int experienceMax,
        LocalDateTime closingDate
) {
}