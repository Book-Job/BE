package com.bookjob.job.service;

import com.bookjob.common.exception.NotFoundException;
import com.bookjob.job.domain.JobSeeking;
import com.bookjob.job.domain.JobSeekingOrder;
import com.bookjob.job.dto.response.CursorJobSeekingResponse;
import com.bookjob.job.dto.response.JobSeekingDetailsResponse;
import com.bookjob.job.dto.response.JobSeekingPreviewResponse;
import com.bookjob.job.repository.JobSeekingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JobSeekingReadService {

    private final JobSeekingRepository jobSeekingRepository;

    public CursorJobSeekingResponse getJobSeekings(JobSeekingOrder order, Long cursor, int pageSize) {
        String jobCategory = extractCategoryFromOrder(order); // null이면 전체
        List<JobSeekingPreviewResponse> jobSeekings = jobSeekingRepository
                .getJobSeekingsOrderedBy(cursor, jobCategory, pageSize);
        Long newCursor = jobSeekings.get(jobSeekings.size() - 1).id();
        return new CursorJobSeekingResponse(jobSeekings, newCursor);
    }

    public JobSeekingDetailsResponse getJobSeekingDetails(Long id) {
        JobSeeking jobSeeking = jobSeekingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(NotFoundException::jobSeekingNotFound);

        jobSeeking.incrementViewCount();

        return new JobSeekingDetailsResponse(
                jobSeeking.getId(),
                jobSeeking.getMemberId(),
                jobSeeking.getNickname(),
                jobSeeking.getTitle(),
                jobSeeking.getText(),
                jobSeeking.getViewCount(),
                jobSeeking.getExperience(),
                jobSeeking.getEmploymentType().toString(),
                jobSeeking.getJobCategory().toString(),
                jobSeeking.getContactEmail(),
                jobSeeking.getCreatedAt(),
                jobSeeking.getModifiedAt()
        );
    }

    private String extractCategoryFromOrder(JobSeekingOrder order) {
        if (order == JobSeekingOrder.LATEST) return null;
        return order.name().replace("_LATEST", ""); // e.g., EDITOR_LATEST → EDITOR
    }
}
