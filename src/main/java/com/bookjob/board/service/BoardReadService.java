package com.bookjob.board.service;

import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.BoardPreviewResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.repository.BoardQueryRepository;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MyPostingsInBoard;
import com.bookjob.member.dto.MyPostingsInBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardReadService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    public CursorBoardResponse getBoardsAfterCursorWithKeyword(String keyword, Long cursor, int pageSize) {
        Long cursorId = cursor != null ? cursor : java.lang.Long.MAX_VALUE;

        List<BoardPreviewResponse> boards = boardQueryRepository.getBoardListWithKeyword(keyword, cursorId, pageSize);

        if (boards.isEmpty()) {
            return new CursorBoardResponse(List.of(), 0L);
        }

        Long lastBoardId = boards.getLast().boardId();

        return new CursorBoardResponse(boards, lastBoardId);
    }

    public BoardDetailResponse getBoardDetails(Long boardId) {
        return boardRepository.findBoardById(boardId).orElseThrow(
                () -> NotFoundException.boardNotFound(boardId)
        );
    }

    public boolean notExistsBoard(Long boardId) {
        return !boardRepository.existsById(boardId);
    }

    public MyPostingsInBoardResponse getMyPostingsInBoard(Member member) {
        List<MyPostingsInBoard> myPostingsInBoardList =
                boardRepository.findMyPostingsByMemberId(member.getId());
        return new MyPostingsInBoardResponse(myPostingsInBoardList);
    }
}
