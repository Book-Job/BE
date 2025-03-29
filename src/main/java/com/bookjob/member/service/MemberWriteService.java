package com.bookjob.member.service;

import com.bookjob.common.domain.Password;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MemberSignupRequest;
import com.bookjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerMember(MemberSignupRequest request) {

        Member member = Member.builder()
                .loginId(request.loginId())
                .nickname(request.nickname())
                .email(request.email())
                .password(Password.of(request.password(), passwordEncoder))
                .build();

        memberRepository.save(member);
    }
}
