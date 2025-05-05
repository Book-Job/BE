package com.bookjob.job.facade;

import com.bookjob.common.exception.BadRequestException;
import com.bookjob.job.dto.request.RecruitmentDeleteRequest;
import com.bookjob.job.dto.request.RecruitmentIdsRequest;
import com.bookjob.job.dto.response.MyPostingsInRecruitmentResponse;
import com.bookjob.job.service.JobPostingWriteService;
import com.bookjob.job.service.JobSeekingWriteService;
import com.bookjob.job.service.RecruitmentService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitmentFacade {

    private final RecruitmentService recruitmentService;
    private final JobPostingWriteService jobPostingWriteService;
    private final JobSeekingWriteService jobSeekingWriteService;

    private final String CATEGORY_JOB_POSTING = "JOB_POSTING";
    private final String CATEGORY_JOB_SEEKING = "JOB_SEEKING";

    public MyPostingsInRecruitmentResponse getMyPostingsInRecruitments(Member member, int page, int limit) {
        return recruitmentService.getMyPostingsInRecruitments(member, page, limit);
    }

    public void deleteMyPostingsInRecruitments(Member member, RecruitmentIdsRequest request) {
        for(RecruitmentDeleteRequest deleteRequest: request.deleteRequest()) {
            Long id = deleteRequest.recruitmentId();
            String category = deleteRequest.recruitmentCategory();

            if (CATEGORY_JOB_POSTING.equalsIgnoreCase(category)) {
                jobPostingWriteService.deleteJobPosting(id, member);
            } else if (CATEGORY_JOB_SEEKING.equalsIgnoreCase(category)) {
                jobSeekingWriteService.deleteJobSeeking(id, member);
            } else {
                throw BadRequestException.invalidJobCategory(category);
            }
        }
    }
}
