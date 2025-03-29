package com.bookjob.member.facade;

import com.bookjob.member.dto.MemberSignupRequest;
import com.bookjob.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberWriteService memberWriteService;

    public void saveMember(MemberSignupRequest request) {
        memberWriteService.registerMember(request);
    }
}
