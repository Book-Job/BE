package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.dto.BoardUpdateRequest;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.domain.Member;
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
        Board board = boardRepository.findById(boardId).orElseThrow(
                NotFoundException::boardNotFound
        );

        if (!board.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        board.updateText(request.text());
    }

    public void deleteBoard(Long boardId, Member member) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                NotFoundException::boardNotFound
        );

        if (!board.getMemberId().equals(member.getId())) {
            throw ForbiddenException.forbidden();
        }

        board.delete();
    }
}
