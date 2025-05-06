package com.bookjob.member.facade;

import com.bookjob.auth.service.AuthService;
import com.bookjob.common.exception.BadRequestException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.OriginalPasswordRequest;
import com.bookjob.member.dto.request.MemberSignupRequest;
import com.bookjob.member.dto.request.UpdateNicknameRequest;
import com.bookjob.member.dto.response.MemberDetailResponse;
import com.bookjob.member.dto.response.MyPageResponse;
import com.bookjob.member.event.MemberWithdrawalEvent;
import com.bookjob.member.service.MemberReadService;
import com.bookjob.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

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

    @Transactional
    public void withdrawMember(Member member, String password) {
        if (!member.getPassword().matches(password, passwordEncoder)) {
            throw BadRequestException.passwordMissmatch();
        }

        memberWriteService.deleteMember(member);

        eventPublisher.publishEvent(new MemberWithdrawalEvent(this, member.getId()));
    }

    public void checkOriginalPassword(Long memberId, OriginalPasswordRequest request) {
        if (!memberReadService.checkPasswordIsIdentical(memberId, request)) {
            throw BadRequestException.passwordMissmatch();
        }
    }
}
