package com.bookjob.member.facade;

import com.bookjob.auth.service.AuthService;
import com.bookjob.board.service.BoardReadService;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.*;
import com.bookjob.member.service.MemberReadService;
import com.bookjob.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;
    private final AuthService authService;
    private final BoardReadService boardReadService;

    public void saveMember(MemberSignupRequest request) {
        memberWriteService.registerMember(request);
    }

    public MyPageResponse getMyPage(Member member) {
        return memberReadService.getMyPage(member);
    }

    public MemberDetailResponse getMemberDetail(Member member) {
        return memberReadService.getMemberDetail(member);
    }

    public void updateNickname(Member member, UpdateNicknameRequest request) {
        authService.checkDuplicatedNickname(request.nickname());
        memberWriteService.updateNickname(member, request);
    }

    public MyPostingsInBoardResponse getMyPostingsInBoard(Member member) {
        return boardReadService.getMyPostingsInBoard(member);
    }
}
