package com.bookjob.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberPasswordLoginRequest(
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        String password
) {
}
