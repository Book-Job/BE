package com.bookjob.job.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.job.dto.request.JobSeekingCreateRequest;
import com.bookjob.job.facade.JobSeekingFacade;
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
@RequestMapping("/api/v1/job-seeking")
public class JobSeekingController {

    private final JobSeekingFacade jobSeekingFacade;

    @PostMapping
    public ResponseEntity<?> createJobSeeking(@RequestBody JobSeekingCreateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobSeekingFacade.createJobSeeking(request, member);
        return ResponseEntity.ok(CommonResponse.success());
    }

}
