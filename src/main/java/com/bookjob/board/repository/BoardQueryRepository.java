package com.bookjob.board.repository;

import com.bookjob.board.dto.response.BoardPreviewResponse;
import com.bookjob.jooq.generated.tables.Board;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bookjob.jooq.generated.tables.Board.BOARD;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final DSLContext dslContext;
    private final Board b = BOARD.as("board");

    public List<BoardPreviewResponse> getBoardListWithKeyword(String keyword, Long cursor, int limit) {
        return dslContext
                .select(
                        b.ID.as("boardId"),
                        b.TITLE,
                        b.TEXT,
                        b.NICKNAME,
                        b.VIEW_COUNT,
                        b.IS_AUTHENTIC,
                        b.COMMENT_COUNT,
                        b.CREATED_AT,
                        b.MODIFIED_AT
                )
                .from(b)
                .where(boardListKeywordCondition(keyword)
                        .and(boardIdCursorCondition(cursor))
                        .and(BOARD.DELETED_AT.isNull()))
                .orderBy(b.CREATED_AT.desc())
                .limit(limit)
                .fetchInto(BoardPreviewResponse.class);
    }

    private Condition boardListKeywordCondition(String keyword) {
        Condition result = noCondition();

        if (keyword == null || keyword.isEmpty()) {
            return result;
        }

        return result.and(
                b.TITLE.like(concat(inline("%"), val(keyword), inline("%")))
                        .or(b.TEXT.like(concat(inline("%"), val(keyword), inline("%"))))
        );
    }

    private Condition boardIdCursorCondition(Long cursor) {
        Long maxCondition = Long.MAX_VALUE;
        Condition result = noCondition();

        if (cursor == null || cursor == 0L) {
            return result.and(b.ID.lessThan(maxCondition));
        }

        return result.and(b.ID.lessThan(cursor));
    }
}
