package com.bookjob.member.dto;

public record MemberDetailResponse (
        String nickname,
        String email,
        String loginId
) {
}
