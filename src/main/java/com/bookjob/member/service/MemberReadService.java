package com.bookjob.member.service;

import com.bookjob.common.exception.NotFoundException;
import com.bookjob.common.exception.UnAuthorizedException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MyPageResponse;
import com.bookjob.member.repository.MemberRepository;
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
        if (loginId == null || loginId.isBlank()) {
            throw NotFoundException.loginIdNotFound();
        }

        int visible = 2;
        String maskedLoginId = "*".repeat(loginId.length() - visible);
        return loginId.substring(0, visible) + maskedLoginId;
    }

    public String getMaskedEmail(String loginId) {
        String email = memberRepository.findEmailByLoginId(loginId);
        if (email == null || email.isBlank()) {
            throw NotFoundException.emailNotFound();
        }

        return maskEmail(email);
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf("@");

        String localPart = email.substring(0, atIndex); // "abcde"
        String domainPart = email.substring(atIndex + 1); // "domain.com"

        // 마스킹된 local part
        int visibleLocal = Math.min(2, localPart.length());
        String maskedLocal = localPart.substring(0, visibleLocal)
                + "*".repeat(localPart.length() - visibleLocal);

        // 마스킹된 domain part
        int dotIndex = domainPart.lastIndexOf(".");

        String domainName = domainPart.substring(0, dotIndex); // "domain"
        String domainSuffix = domainPart.substring(dotIndex);  // ".com"

        int visibleDomain = 1;
        String maskedDomain = domainName.substring(0, visibleDomain)
                + "*".repeat(domainName.length() - visibleDomain);

        return maskedLocal + "@" + maskedDomain + domainSuffix;
    }

    public MyPageResponse getMyPage(Member member) {
        return memberRepository.getMyPageById(member.getId());
    }
}
