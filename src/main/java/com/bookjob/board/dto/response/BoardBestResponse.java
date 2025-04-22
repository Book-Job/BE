package com.bookjob.board.dto.response;

import com.bookjob.board.domain.Board;
import lombok.Getter;

@Getter
public class BoardBestResponse {
    private final Long boardId;
    private final String title;
    private final Long commentCount;

    public BoardBestResponse(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.commentCount = (board.getCommentCount() != null) ? board.getCommentCount() : 0;
    }
}
