package com.bookjob.board.facade;

import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.dto.CursorBoardResponse;
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
}
