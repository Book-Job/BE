package com.bookjob.job.facade;

import com.bookjob.job.dto.request.JobSeekingCreateRequest;
import com.bookjob.job.service.JobSeekingWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobSeekingFacade {

    private final JobSeekingWriteService jobSeekingWriteService;

    public void createJobSeeking(JobSeekingCreateRequest request, Member member) {
        jobSeekingWriteService.createJobSeeking(request, member);
    }
}
