package com.bookjob.board.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardCreateRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,

        @NotBlank(message = "글 내용은 필수 입력 값입니다.")
        String text,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        String nickname
) {
}
