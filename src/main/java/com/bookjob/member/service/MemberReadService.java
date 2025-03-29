package com.bookjob.member.service;

import com.bookjob.common.exception.UnAuthorizedException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository boardRepository;

    public Member getActiveMemberById(Long id) {
        return boardRepository.findByIdAndIsBlockedFalseAndDeletedAtIsNull(id).orElseThrow(
                UnAuthorizedException::deactivatedMemberUnauthorized
        );
    }
}
