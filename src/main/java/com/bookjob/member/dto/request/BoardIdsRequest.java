package com.bookjob.member.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BoardIdsRequest (
        @NotEmpty(message = "게시글 ID 리스트는 비어 있을 수 없습니다.")
        List<Long> ids
) {
}
