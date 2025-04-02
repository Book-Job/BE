package com.bookjob.board.dto.response;

import java.util.List;

public record CursorBoardResponse(
        List<BoardPreviewResponse> boards,
        Long lastBoardId
) {
}