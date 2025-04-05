package com.bookjob.job.facade;

import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.service.JobPostingWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingFacade {

    private final JobPostingWriteService jobPostingWriteService;

    public void createJobPosting(JobPostingCreateRequest request, Member member){
        jobPostingWriteService.createJobPosting(request, member);
    }
}
