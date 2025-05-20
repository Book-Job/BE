package com.bookjob.job.facade;

import com.bookjob.job.domain.JobSeekingOrder;
import com.bookjob.job.dto.request.JobSeekingCreateRequest;
import com.bookjob.job.dto.request.JobSeekingUpdateRequest;
import com.bookjob.job.dto.response.CursorJobSeekingResponse;
import com.bookjob.job.dto.response.JobSeekingDetailsResponse;
import com.bookjob.job.service.JobSeekingReadService;
import com.bookjob.job.service.JobSeekingWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobSeekingFacade {

    private final JobSeekingWriteService jobSeekingWriteService;
    private final JobSeekingReadService jobSeekingReadService;

    public void createJobSeeking(JobSeekingCreateRequest request, Member member) {
        jobSeekingWriteService.createJobSeeking(request, member);
    }

    public CursorJobSeekingResponse getJobSeekings(String order, Long cursor, String keyword, int pageSize) {
        JobSeekingOrder jobSeekingOrder = JobSeekingOrder.fromString(order);
        return jobSeekingReadService.getJobSeekings(jobSeekingOrder, cursor, keyword, pageSize);
    }

    public JobSeekingDetailsResponse getJobSeekingDetails(Long id) {
        return jobSeekingReadService.getJobSeekingDetails(id);
    }

    public void updateJobSeeking(Long id, JobSeekingUpdateRequest request, Member member) {
        jobSeekingWriteService.updateJobSeeking(id, request, member);
    }

    public void deleteJobSeeking(Long id, Member member) {
        jobSeekingWriteService.deleteJobSeeking(id, member);
    }
}
