package com.bookjob.member.facade;

import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MemberDetailResponse;
import com.bookjob.member.dto.MemberSignupRequest;
import com.bookjob.member.dto.MyPageResponse;
import com.bookjob.member.service.MemberReadService;
import com.bookjob.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    public void saveMember(MemberSignupRequest request) {
        memberWriteService.registerMember(request);
    }

    public MyPageResponse getMyPage(Member member) {
        return memberReadService.getMyPage(member);
    }

    public MemberDetailResponse getMemberDetail(Member member) {
        return memberReadService.getMemberDetail(member);
    }
}
