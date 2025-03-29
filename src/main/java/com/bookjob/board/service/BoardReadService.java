package com.bookjob.board.service;

import com.bookjob.board.dto.BoardPreviewResponse;
import com.bookjob.board.dto.CursorBoardResponse;
import com.bookjob.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardReadService {

    private final BoardRepository boardRepository;

    public CursorBoardResponse getBoardsAfterCursor(Long cursor, int pageSize) {
        Long cursorId = cursor != null ? cursor : Long.MAX_VALUE;

        Pageable pageable = PageRequest.of(0, pageSize);
        List<BoardPreviewResponse> boards = boardRepository.findBoardsBeforeCursor(cursorId, pageable);

        if (boards.isEmpty()) {
            return new CursorBoardResponse(List.of(), 0L);
        }

        Long lastBoardId = boards.getLast().boardId();

        return new CursorBoardResponse(boards, lastBoardId);
    }
}
