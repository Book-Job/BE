package com.bookjob.member.dto.response;

public record MemberDetailResponse (
        String nickname,
        String email,
        String loginId
) {
}
