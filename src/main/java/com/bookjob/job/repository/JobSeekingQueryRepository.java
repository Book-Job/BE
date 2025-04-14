package com.bookjob.job.repository;

import com.bookjob.job.dto.response.JobSeekingPreviewResponse;

import java.util.List;

public interface JobSeekingQueryRepository {
    List<JobSeekingPreviewResponse> getJobSeekingsOrderedBy(Long cursor, String jobCategory, int size);
}
