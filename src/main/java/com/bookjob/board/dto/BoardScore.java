package com.bookjob.board.dto;

import com.bookjob.board.domain.Board;

public record BoardScore (
        Board board,
        Double score
) {
}
