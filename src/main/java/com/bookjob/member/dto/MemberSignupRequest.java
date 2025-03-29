package com.bookjob.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 영문과 숫자만 사용하며, 4~12자까지 입력 가능합니다.")
        String loginId,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$", message = "닉네임은 특수문자를 제외한 2~8자까지 입력 가능합니다.")
        String nickname,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "비밀번호는 영문과 숫자를 포함해야 합니다.")
        String password
) {}
