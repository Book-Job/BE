package com.bookjob.job.dto.request;

public record JobSeekingCreateRequest (
        String title,
        String text,
        String employmentType,
        String jobCategory,
        String writer,
        String contactEmail,
        String experience
) {
}
