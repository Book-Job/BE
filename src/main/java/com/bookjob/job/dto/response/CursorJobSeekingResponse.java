package com.bookjob.job.dto.response;

import java.util.List;

public record CursorJobSeekingResponse (
        List<JobSeekingPreviewResponse> jobSeekings,
        Long lastId
) {
}
