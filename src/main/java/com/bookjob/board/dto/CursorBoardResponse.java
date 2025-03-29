package com.bookjob.board.dto;

import java.util.List;

public record CursorBoardResponse(
        List<BoardPreviewResponse> boards,
        Long lastBoardId
) {
}