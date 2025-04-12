package com.bookjob.job.service;

import com.bookjob.common.exception.NotFoundException;
import com.bookjob.job.domain.JobPosting;
import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.dto.response.JobPostingDetailsResponse;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;
import com.bookjob.job.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobPostingReadService {

    private final JobPostingRepository jobPostingRepository;

    public CursorJobPostingResponse getJobPostings(JobPostingOrder order, Long cursor, int pageSize) {
        List<JobPostingPreviewResponse> response = jobPostingRepository.getJobPostingsOrderedBy(order, cursor, pageSize);
        if (response.isEmpty()) {
            return new CursorJobPostingResponse(response, null);
        }
        Long newCursor = response.getLast().id();

        return new CursorJobPostingResponse(response, newCursor);
    }

    public JobPostingDetailsResponse getJobPostingDetails(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(
                NotFoundException::jobPostingNotFound
        );

        return new JobPostingDetailsResponse(
                jobPosting.getId(),
                jobPosting.getNickname(),
                jobPosting.getTitle(),
                jobPosting.getText(),
                jobPosting.getViewCount(),
                jobPosting.getWebsiteUrl(),
                jobPosting.getExperienceMin(),
                jobPosting.getExperienceMax(),
                jobPosting.getClosingDate(),
                jobPosting.getEmploymentType().toString(),
                jobPosting.getJobCategory().toString(),
                jobPosting.getLocation(),
                jobPosting.getCreatedAt(),
                jobPosting.getModifiedAt()
        );
    }
}
