package com.bookjob.job.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.job.dto.request.RecruitmentIdsRequest;
import com.bookjob.job.dto.response.MyPostingsInRecruitmentResponse;
import com.bookjob.job.facade.RecruitmentFacade;
import com.bookjob.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruitments")
public class RecruitmentController {

    private final RecruitmentFacade recruitmentFacade;

    /**
     * 내가 쓴 구인구직글 조회 및 선택 삭제
     */
    @GetMapping("/members")
    public ResponseEntity<?> getMyPostingsInRecruitments(@AuthenticationPrincipal(expression = "member") Member member,
                                                         @RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int limit) {
        MyPostingsInRecruitmentResponse myPostingsInBoardResponse = recruitmentFacade.getMyPostingsInRecruitments(member, page, limit);
        return ResponseEntity.ok(CommonResponse.success(myPostingsInBoardResponse));
    }

    @DeleteMapping("/members")
    public ResponseEntity<?> deleteMyPostingsInRecruitments(@AuthenticationPrincipal(expression = "member") Member member,
                                                            @Valid @RequestBody RecruitmentIdsRequest request) {
        recruitmentFacade.deleteMyPostingsInRecruitments(member, request);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
