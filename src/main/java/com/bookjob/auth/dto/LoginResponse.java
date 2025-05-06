package com.bookjob.auth.dto;

public record LoginResponse(
        String nickname,
        String loginId
) {
}
