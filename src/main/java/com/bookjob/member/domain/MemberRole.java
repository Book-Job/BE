package com.bookjob.member.domain;

public enum MemberRole {
    MEMBER ("회원");

    private String name;

    MemberRole(String name) {
        this.name = name;
    }
}
