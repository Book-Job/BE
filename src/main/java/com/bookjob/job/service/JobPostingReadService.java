package com.bookjob.job.service;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;
import com.bookjob.job.repository.JobPostingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobPostingReadService {

    private final JobPostingQueryRepository jobPostingQueryRepository;

    public CursorJobPostingResponse getJobPostings(JobPostingOrder order, Long cursor, int pageSize) {
        List<JobPostingPreviewResponse> response = jobPostingQueryRepository.getJobPostingsOrderedBy(order, cursor, pageSize);
        if(response.isEmpty()){
            return new CursorJobPostingResponse(response, null);
        }
        Long newCursor = response.getLast().id();

        return new CursorJobPostingResponse(response, newCursor);
    }
}
