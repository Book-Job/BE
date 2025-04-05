package com.bookjob.member.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    MEMBER ("회원");

    private final String name;

    MemberRole(String name) {
        this.name = name;
    }

}
