package com.bookjob.job.facade;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.dto.request.JobPostingUpdateRequest;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.dto.response.JobPostingBestResponse;
import com.bookjob.job.dto.response.JobPostingDetailsResponse;
import com.bookjob.job.service.JobPostingReadService;
import com.bookjob.job.service.JobPostingWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobPostingFacade {

    private final JobPostingWriteService jobPostingWriteService;
    private final JobPostingReadService jobPostingReadService;

    public void createJobPosting(JobPostingCreateRequest request, Member member) {
        jobPostingWriteService.createJobPosting(request, member);
    }

    public CursorJobPostingResponse getJobPostings(String order, Long cursor, int pageSize, String keyword) {
        JobPostingOrder jobPostingOrder = JobPostingOrder.fromString(order);

        return jobPostingReadService.getJobPostings(jobPostingOrder, cursor, pageSize, keyword);
    }

    public void updateJobPosting(Long id, JobPostingUpdateRequest request, Member member) {
        jobPostingWriteService.updateJobPosting(id, request, member);
    }

    public void deleteJobPosting(Long id, Member member) {
        jobPostingWriteService.deleteJobPosting(id, member);
    }

    public JobPostingDetailsResponse getJobPostingDetails(Long id) {
        return jobPostingReadService.getJobPostingDetails(id);
    }

    public List<JobPostingBestResponse> getJobPostingBest() {
        return jobPostingReadService.getJobPostingBest();
    }
}
