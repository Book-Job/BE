package com.bookjob.member.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberWithdrawalEvent extends ApplicationEvent {
    private final Long memberId;

    public MemberWithdrawalEvent(Object source, Long memberId) {
        super(source);
        this.memberId = memberId;
    }
}
