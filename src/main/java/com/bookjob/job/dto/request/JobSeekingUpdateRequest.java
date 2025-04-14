package com.bookjob.job.dto.request;

public record JobSeekingUpdateRequest (
        String title,
        String text,
        String employmentType,
        String jobCategory,
        String contactEmail,
        String experience
) {
}
