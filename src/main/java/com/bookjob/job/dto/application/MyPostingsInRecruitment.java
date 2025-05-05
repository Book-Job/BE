package com.bookjob.job.dto.application;

import java.time.LocalDateTime;

public record MyPostingsInRecruitment(
        Long recruitmentId,
        String title,
        LocalDateTime createdAt,
        String recruitmentCategory
) {
}
