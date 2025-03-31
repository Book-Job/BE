package com.bookjob.board.facade;

import com.bookjob.board.dto.request.BoardCreateRequest;
import com.bookjob.board.dto.request.BoardUpdateRequest;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.service.BoardReadService;
import com.bookjob.board.service.BoardWriteService;
import com.bookjob.member.domain.Member;
import com.bookjob.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardWriteService boardWriteService;
    private final MemberReadService memberReadService;
    private final BoardReadService boardReadService;

    public void createBoard(BoardCreateRequest request, Member member) {
        Member activeMember = memberReadService.getActiveMemberById(member.getId());
        boardWriteService.createBoard(request, activeMember);
    }

    public CursorBoardResponse getBoardsAfterCursor(Long cursor, int pageSize) {
        return boardReadService.getBoardsAfterCursor(cursor, pageSize);
    }

    public void updateBoard(BoardUpdateRequest request, Member member, Long boardId) {
        Member activeMember = memberReadService.getActiveMemberById(member.getId());
        boardWriteService.updateBoard(request, activeMember, boardId);
    }

    public void deleteBoard(Long boardId, Member member) {
        boardWriteService.deleteBoard(boardId, member);
    }

    public BoardDetailResponse getBoardDetail(Long boardId) {
        return boardReadService.getBoardDetails(boardId);
    }
}
