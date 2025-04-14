package com.bookjob.job.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.job.dto.request.JobSeekingCreateRequest;
import com.bookjob.job.dto.request.JobSeekingUpdateRequest;
import com.bookjob.job.dto.response.CursorJobSeekingResponse;
import com.bookjob.job.dto.response.JobSeekingDetailsResponse;
import com.bookjob.job.facade.JobSeekingFacade;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-seeking")
public class JobSeekingController {

    private final JobSeekingFacade jobSeekingFacade;
    private final static int pageSize = 6;

    @PostMapping
    public ResponseEntity<?> createJobSeeking(@RequestBody JobSeekingCreateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobSeekingFacade.createJobSeeking(request, member);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping
    public ResponseEntity<?> getJobSeekings(@RequestParam(required = false) Long last,
                                            @RequestParam(required = false) String order) {
        CursorJobSeekingResponse response = jobSeekingFacade.getJobSeekings(order, last, pageSize);
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @PatchMapping("{jobSeekingId}")
    public ResponseEntity<?> updateJobSeeking(@PathVariable Long jobSeekingId,
                                              @RequestBody JobSeekingUpdateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobSeekingFacade.updateJobSeeking(jobSeekingId, request, member);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping("{jobSeekingId}")
    public ResponseEntity<?> deleteJobSeeking(@PathVariable Long jobSeekingId,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobSeekingFacade.deleteJobSeeking(jobSeekingId, member);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("{jobSeekingId}")
    public ResponseEntity<?> getJobSeeking(@PathVariable Long jobSeekingId) {
        JobSeekingDetailsResponse response = jobSeekingFacade.getJobSeekingDetails(jobSeekingId);
        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
