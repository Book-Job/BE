package com.bookjob.member.controller;

import com.bookjob.auth.dto.TempTokenResponse;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.OriginalPasswordRequest;
import com.bookjob.member.dto.request.ChangePasswordRequest;
import com.bookjob.member.dto.request.DeleteMemberRequest;
import com.bookjob.member.dto.request.MemberSignupRequest;
import com.bookjob.member.dto.request.UpdateNicknameRequest;
import com.bookjob.member.dto.response.MemberDetailResponse;
import com.bookjob.member.dto.response.MyPageResponse;
import com.bookjob.member.facade.MemberFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberSignupRequest request) {
        memberFacade.saveMember(request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal(expression = "member") Member member) {
        MyPageResponse myPageResponse = memberFacade.getMyPage(member);
        return ResponseEntity.ok(CommonResponse.success(myPageResponse));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getMemberDetail(@AuthenticationPrincipal(expression = "member") Member member) {
        MemberDetailResponse memberDetailResponse = memberFacade.getMemberDetail(member);
        return ResponseEntity.ok(CommonResponse.success(memberDetailResponse));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal(expression = "member") Member member,
                                            @Valid @RequestBody UpdateNicknameRequest request) {
        memberFacade.updateNickname(member, request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<?> withdrawMember(@AuthenticationPrincipal(expression = "member") Member member,
                                            @Valid @RequestBody DeleteMemberRequest request) {
        memberFacade.withdrawMember(member, request.password());
        return ResponseEntity.ok(CommonResponse.success());
    }

    @PostMapping("/password")
    public ResponseEntity<?> checkOriginalPassword(@AuthenticationPrincipal(expression = "member") Member member,
                                                   @RequestBody @Valid OriginalPasswordRequest request) {
        String resetToken = memberFacade.checkOriginalPassword(member, request);
        return ResponseEntity.ok(CommonResponse.success(new TempTokenResponse(resetToken)));
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal(expression = "member") Member member,
                                                   @RequestBody @Valid ChangePasswordRequest request) {
        memberFacade.changePassword(member, request);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
