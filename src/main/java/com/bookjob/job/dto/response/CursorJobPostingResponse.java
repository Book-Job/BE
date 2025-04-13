package com.bookjob.job.dto.response;

import java.util.List;

public record CursorJobPostingResponse(
        List<JobPostingPreviewResponse> jobPostings,
        Long lastId
) {
}
