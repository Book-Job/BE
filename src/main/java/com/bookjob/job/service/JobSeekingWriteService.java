package com.bookjob.job.service;

import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.job.domain.EmploymentType;
import com.bookjob.job.domain.JobCategory;
import com.bookjob.job.domain.JobPosting;
import com.bookjob.job.domain.JobSeeking;
import com.bookjob.job.dto.request.JobPostingUpdateRequest;
import com.bookjob.job.dto.request.JobSeekingCreateRequest;
import com.bookjob.job.dto.request.JobSeekingUpdateRequest;
import com.bookjob.job.repository.JobSeekingRepository;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobSeekingWriteService {

    private final JobSeekingRepository jobSeekingRepository;

    public void createJobSeeking(JobSeekingCreateRequest request, Member member) {
        JobCategory jobCategory = JobCategory.fromString(request.jobCategory());
        EmploymentType employmentType = EmploymentType.fromString(request.employmentType());

        JobSeeking jobSeeking = JobSeeking.builder()
                .title(request.title())
                .text(request.text())
                .contactEmail(request.contactEmail())
                .experience(request.experience())
                .employmentType(employmentType)
                .jobCategory(jobCategory)
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        jobSeekingRepository.save(jobSeeking);
    }

    public void updateJobSeeking(Long id, JobSeekingUpdateRequest request, Member member) {
        JobCategory jobCategory = JobCategory.fromString(request.jobCategory());
        EmploymentType employmentType = EmploymentType.fromString(request.employmentType());

        JobSeeking jobSeeking = jobSeekingRepository.findById(id).orElseThrow(NotFoundException::jobSeekingNotFound);

        if (!jobSeeking.getMemberId().equals(member.getId())) {throw ForbiddenException.forbidden();}

        jobSeeking.update(
                request.title(),
                request.text(),
                employmentType,
                jobCategory,
                request.contactEmail(),
                request.experience()
        );
    }

    public void deleteJobSeeking(Long id, Member member) {
        JobSeeking jobSeeking = jobSeekingRepository.findById(id).orElseThrow(NotFoundException::jobSeekingNotFound);

        if (!jobSeeking.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        jobSeeking.softDelete();
    }
}
