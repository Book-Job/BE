package com.bookjob.job.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.facade.JobPostingFacade;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-posting")
public class JobPostingController {

    private final JobPostingFacade jobPostingFacade;

    @PostMapping
    public ResponseEntity<?> createJobPosting(@RequestBody JobPostingCreateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobPostingFacade.createJobPosting(request, member);

        return ResponseEntity.ok(CommonResponse.success());
    }
}
