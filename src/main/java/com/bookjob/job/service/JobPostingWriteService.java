package com.bookjob.job.service;

import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.job.domain.EmploymentType;
import com.bookjob.job.domain.JobCategory;
import com.bookjob.job.domain.JobPosting;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.dto.request.JobPostingUpdateRequest;
import com.bookjob.job.repository.JobPostingRepository;
import com.bookjob.member.annotation.MemberDataCleanup;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobPostingWriteService {

    private final JobPostingRepository jobPostingRepository;

    public void createJobPosting(JobPostingCreateRequest request, Member member) {
        JobCategory jobCategory = JobCategory.fromString(request.jobCategory());
        EmploymentType employmentType = EmploymentType.fromString(request.employmentType());

        JobPosting jobPosting = JobPosting.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .text(request.text())
                .closingDate(request.closingDate())
                .websiteUrl(request.websiteUrl())
                .jobCategory(jobCategory)
                .title(request.title())
                .employmentType(employmentType)
                .isCrawling(false)
                .location(request.location())
                .experienceMax(request.experienceMax())
                .experienceMin(request.experienceMin())
                .build();

        jobPostingRepository.save(jobPosting);
    }

    public void updateJobPosting(Long id, JobPostingUpdateRequest request, Member member) {
        JobCategory jobCategory = JobCategory.fromString(request.jobCategory());
        EmploymentType employmentType = EmploymentType.fromString(request.employmentType());

        JobPosting jobPosting = jobPostingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(NotFoundException::jobPostingNotFound);

        if (!jobPosting.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        jobPosting.update(
                request.title(),
                request.text(),
                request.websiteUrl(),
                request.experienceMin(),
                request.experienceMax(),
                request.closingDate(),
                employmentType,
                jobCategory,
                request.location()
        );
    }

    public void deleteJobPosting(Long id, Member member) {
        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(NotFoundException::jobPostingNotFound);

        if (!jobPosting.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        jobPosting.softDelete();
    }

    @MemberDataCleanup(order = 3)
    public void deleteJobPosting(Long memberId) {
        jobPostingRepository.deleteAllByMemberId(memberId);
    }
}
