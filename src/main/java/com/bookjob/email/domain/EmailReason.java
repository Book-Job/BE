package com.bookjob.email.domain;

import lombok.Getter;

@Getter
public enum EmailReason {
    REGISTER("회원가입"),
    LOGINID("아이디 찾기"),
    PASSWORD("비밀번호 찾기");

    private final String reason;

    EmailReason(String reason) {
        this.reason = reason;
    }

    public static EmailReason fromString(String reason) {
        for(EmailReason r : EmailReason.values()) {
            if (r.getReason().equals(reason)) {
                return r;
            }
        }
        return null;
    }

}
