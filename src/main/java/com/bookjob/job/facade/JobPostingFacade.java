package com.bookjob.job.facade;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.dto.request.JobPostingUpdateRequest;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.service.JobPostingReadService;
import com.bookjob.job.service.JobPostingWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingFacade {

    private final JobPostingWriteService jobPostingWriteService;
    private final JobPostingReadService jobPostingReadService;

    public void createJobPosting(JobPostingCreateRequest request, Member member){
        jobPostingWriteService.createJobPosting(request, member);
    }

    public CursorJobPostingResponse getJobPostings(String order, Long cursor, int pageSize) {
        JobPostingOrder jobPostingOrder = JobPostingOrder.fromString(order);

        return jobPostingReadService.getJobPostings(jobPostingOrder, cursor, pageSize);
    }

    public void updateJobPosting(Long id, JobPostingUpdateRequest request, Member member) {
        jobPostingWriteService.updateJobPosting(id, request, member);
    }
}
