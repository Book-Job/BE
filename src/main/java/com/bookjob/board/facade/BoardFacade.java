package com.bookjob.board.facade;

import com.bookjob.board.dto.request.BoardCreateRequest;
import com.bookjob.board.dto.request.BoardUpdateRequest;
import com.bookjob.board.dto.response.BoardBestResponse;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.service.BoardReadService;
import com.bookjob.board.service.BoardWriteService;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.BoardIdsRequest;
import com.bookjob.member.dto.MyPostingsInBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<BoardBestResponse> getBoardBest() {
        return boardReadService.getBoardBest();
    }

    public MyPostingsInBoardResponse getMyPostingsInBoard(Member member) {
        return boardReadService.getMyPostingsInBoard(member);
    }

    public void deleteMyPostingsInBoard(Member member, BoardIdsRequest request) {
        boardWriteService.deleteMyPostingsInBoard(member, request);
    }
}
