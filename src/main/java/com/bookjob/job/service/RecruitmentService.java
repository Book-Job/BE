package com.bookjob.job.service;

import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import com.bookjob.job.dto.response.MyPostingsInRecruitmentResponse;
import com.bookjob.job.repository.JobPostingRepository;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentService {

    private final JobPostingRepository jobPostingRepository;

    public MyPostingsInRecruitmentResponse getMyPostingsInRecruitments(Member member, int page, int limit) {
        List<MyPostingsInRecruitment> MyJobPostingsInRecruitment =
                jobPostingRepository.findMyPostingsByMemberId(member.getId(), page, limit);

        if (MyJobPostingsInRecruitment == null) {
            MyJobPostingsInRecruitment = Collections.emptyList();
        }

        return new MyPostingsInRecruitmentResponse(MyJobPostingsInRecruitment);
    }

}
