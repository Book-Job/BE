package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.request.BoardCreateRequest;
import com.bookjob.board.dto.request.BoardUpdateRequest;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.annotation.MemberDataCleanup;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.request.BoardIdsRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardWriteService {

    private final BoardRepository boardRepository;

    public void createBoard(BoardCreateRequest request, Member member) {
        boolean isSameNickname = request.nickname().equals(member.getNickname());

        Board board = Board.builder()
                .title(request.title())
                .isAuthentic(isSameNickname)
                .text(request.text())
                .nickname(isSameNickname ? member.getNickname() : request.nickname())
                .memberId(member.getId())
                .build();

        boardRepository.save(board);
    }

    public void updateBoard(BoardUpdateRequest request, Member member, Long boardId) {
        Board board = boardRepository.findBoardByIdAndDeletedAtIsNull(boardId)
                .orElseThrow(() -> NotFoundException.boardNotFound(boardId));

        if (!board.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        board.updateText(request.text());
    }

    public void deleteBoard(Long boardId, Member member) {
        Board board = boardRepository.findBoardByIdAndDeletedAtIsNull(boardId)
                .orElseThrow(() -> NotFoundException.boardNotFound(boardId));

        if (!board.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        board.delete();
    }

    public void deleteMyPostingsInBoard(Member member, BoardIdsRequest request) {
        for (Long boardId : request.ids()) {
            deleteBoard(boardId, member);
        }
    }

    public void increaseCommentCount(Long boardId) {
        Board board = boardRepository.findBoardByIdAndDeletedAtIsNull(boardId)
                .orElseThrow(() -> NotFoundException.boardNotFound(boardId));

        board.increaseCommentCount();
    }

    public void decreaseCommentCount(Long boardId) {
        Board board = boardRepository.findBoardByIdAndDeletedAtIsNull(boardId)
                .orElseThrow(() -> NotFoundException.boardNotFound(boardId));

        board.decreaseCommentCount();
    }

    @MemberDataCleanup(order = 5)
    public void deleteAllByMemberId(Long memberId) {
        boardRepository.deleteAllByMemberId(memberId);
    }
}
