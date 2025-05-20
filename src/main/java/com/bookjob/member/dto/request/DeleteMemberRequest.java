package com.bookjob.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteMemberRequest(
        @NotBlank
        String password
) {
}
