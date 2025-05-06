package com.bookjob.member.dto;

import jakarta.validation.constraints.NotBlank;

public record OriginalPasswordRequest (
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {
}
