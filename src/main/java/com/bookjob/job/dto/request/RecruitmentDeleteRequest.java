package com.bookjob.job.dto.request;

public record RecruitmentDeleteRequest (
        Long recruitmentId,
        String recruitmentCategory
) {
}
