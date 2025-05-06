package com.bookjob.job.repository;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobPostingQueryRepository {
    List<JobPostingPreviewResponse> getJobPostingsOrderedBy(JobPostingOrder order, Long cursor, int size);

    Page<MyPostingsInRecruitment> findMyPostingsByMemberId(Long id, Pageable pageable);
}