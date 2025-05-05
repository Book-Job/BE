package com.bookjob.job.repository;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;

import java.util.List;

public interface JobPostingQueryRepository {
    List<JobPostingPreviewResponse> getJobPostingsOrderedBy(JobPostingOrder order, Long cursor, int size);

    List<MyPostingsInRecruitment> findMyPostingsByMemberId(Long id, int page, int limit);
}