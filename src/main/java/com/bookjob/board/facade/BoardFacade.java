package com.bookjob.board.facade;

import com.bookjob.board.dto.request.BoardCreateRequest;
import com.bookjob.board.dto.request.BoardUpdateRequest;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.service.BoardReadService;
import com.bookjob.board.service.BoardWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardWriteService boardWriteService;
    private final BoardReadService boardReadService;

    public void createBoard(BoardCreateRequest request, Member member) {
        boardWriteService.createBoard(request, member);
    }

    public CursorBoardResponse getBoardsAfterCursorWithKeyword(String keyword, Long cursor, int pageSize) {
        return boardReadService.getBoardsAfterCursorWithKeyword(keyword, cursor, pageSize);
    }

    public void updateBoard(BoardUpdateRequest request, Member member, Long boardId) {
        boardWriteService.updateBoard(request, member, boardId);
    }

    public void deleteBoard(Long boardId, Member member) {
        boardWriteService.deleteBoard(boardId, member);
    }

    public BoardDetailResponse getBoardDetail(Long boardId) {
        return boardReadService.getBoardDetails(boardId);
    }
}
