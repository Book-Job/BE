package com.bookjob.member.service;

import com.bookjob.common.exception.UnAuthorizedException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.repository.MemberRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReadService {

    private final MemberRepository boardRepository;
    private final MemberRepository memberRepository;

    public Member getActiveMemberById(Long id) {
        return boardRepository.findByIdAndIsBlockedFalseAndDeletedAtIsNull(id).orElseThrow(
                UnAuthorizedException::deactivatedMemberUnauthorized
        );
    }

    public String getMaskedLoginId(String email) {
        String loginId = memberRepository.findLoginIdByEmail(email);
        int visible = 2;
        String maskedLoginId = "*".repeat(loginId.length() - visible);
        return loginId.substring(0, visible) + maskedLoginId;
    }
}
