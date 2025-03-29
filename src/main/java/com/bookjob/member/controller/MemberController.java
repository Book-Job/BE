package com.bookjob.member.controller;

import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.facade.MemberFacade;
import com.bookjob.member.dto.MemberSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(@Valid @RequestBody MemberSignupRequest request) {
        memberFacade.saveMember(request);

        return ResponseEntity.ok(CommonResponse.success());
    }
}
