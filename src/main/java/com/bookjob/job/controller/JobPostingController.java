package com.bookjob.job.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.dto.request.JobPostingUpdateRequest;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.dto.response.JobPostingDetailsResponse;
import com.bookjob.job.facade.JobPostingFacade;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-posting")
public class JobPostingController {

    private final JobPostingFacade jobPostingFacade;
    private final static int pageSize = 6;

    @PostMapping
    public ResponseEntity<?> createJobPosting(@RequestBody JobPostingCreateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobPostingFacade.createJobPosting(request, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping
    public ResponseEntity<?> getJobPostings(@RequestParam(required = false) Long last,
                                            @RequestParam(required = false) String order,
                                            @RequestParam(required = false) String keyword) {
        CursorJobPostingResponse response = jobPostingFacade.getJobPostings(order, last, pageSize, keyword);

        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @PatchMapping("{jobPostingId}")
    public ResponseEntity<?> updateJobPosting(@PathVariable Long jobPostingId,
                                              @RequestBody JobPostingUpdateRequest request,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobPostingFacade.updateJobPosting(jobPostingId, request, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping("{jobPostingId}")
    public ResponseEntity<?> deleteJobPosting(@PathVariable Long jobPostingId,
                                              @AuthenticationPrincipal(expression = "member") Member member) {
        jobPostingFacade.deleteJobPosting(jobPostingId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("{jobPostingId}")
    public ResponseEntity<?> getJobPosting(@PathVariable Long jobPostingId) {
        JobPostingDetailsResponse response = jobPostingFacade.getJobPostingDetails(jobPostingId);

        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @GetMapping("/best")
    public ResponseEntity<?> getJobPostingBest() {
        return ResponseEntity.ok(CommonResponse.success(jobPostingFacade.getJobPostingBest()));
    }
}
