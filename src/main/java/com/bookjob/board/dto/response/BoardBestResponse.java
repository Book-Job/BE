package com.bookjob.board.dto.response;

import lombok.Getter;

@Getter
public class BoardBestResponse {
    private final String title;
    private final Long commentCount;

    public BoardBestResponse(String title, Long commentCount) {
        this.title = title;
        this.commentCount = (commentCount != null) ? commentCount : 0;
    }
}
