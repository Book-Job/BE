package com.bookjob.job.service;

import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import com.bookjob.job.dto.response.MyPostingsInRecruitmentResponse;
import com.bookjob.job.repository.JobPostingRepository;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentService {

    private final JobPostingRepository jobPostingRepository;

    public MyPostingsInRecruitmentResponse getMyPostingsInRecruitments(Member member, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<MyPostingsInRecruitment> MyJobPostingsInRecruitment =
                jobPostingRepository.findMyPostingsByMemberId(member.getId(), pageable);

        return MyPostingsInRecruitmentResponse.of(MyJobPostingsInRecruitment);
    }

}
