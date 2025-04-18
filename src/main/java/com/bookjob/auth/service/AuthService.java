package com.bookjob.auth.service;

import com.bookjob.common.exception.ConflictException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void checkDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw ConflictException.duplicatedEmail(email);
        }
    }

    public void checkDuplicatedLoginId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw ConflictException.duplicatedLoginId();
        }
    }

    public void checkDuplicatedNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw ConflictException.duplicatedNickname();
        }
    }

    public void doesEmailExist(String email) {
        if (!memberRepository.existsByEmail(email)) {
            throw NotFoundException.emailNotFound();
        }
    }
}
