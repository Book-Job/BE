package com.bookjob.board.repository;

import com.bookjob.board.dto.response.BoardPreviewResponse;
import com.bookjob.jooq.generated.tables.Board;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bookjob.jooq.generated.tables.Board.BOARD;
import static org.jooq.impl.DSL.noCondition;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final DSLContext dslContext;

    public List<BoardPreviewResponse> getBoardListWithKeyword(String keyword, Long cursor, int limit) {
        Board b = BOARD.as("board");

        return dslContext
                .select(
                        b.ID,
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

        return result.and(BOARD.TITLE.like("%" + keyword + "%")).or(BOARD.TEXT.like("%" + keyword + "%"));
    }

    private Condition boardIdCursorCondition(Long cursor) {
        Long maxCondition = Long.MAX_VALUE;
        Condition result = noCondition();

        if (cursor == null || cursor == 0L) {
            return result.and(BOARD.ID.lessThan(maxCondition));
        }

        return result.and(BOARD.ID.lessThan(cursor));
    }
}
