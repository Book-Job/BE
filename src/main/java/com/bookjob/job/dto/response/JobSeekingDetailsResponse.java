package com.bookjob.job.dto.response;

import java.time.LocalDateTime;

public record JobSeekingDetailsResponse (
        long id,
        Long memberId,
        String nickname,
        String title,
        String text,
        int viewCount,
        String experience,
        String employmentType,
        String jobCategory,
        String contactEmail,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
