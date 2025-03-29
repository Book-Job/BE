package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.dto.BoardUpdateRequest;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.member.domain.Member;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardWriteServiceTest {

    @InjectMocks
    private BoardWriteService boardWriteService;

    @Mock
    private BoardRepository boardRepository;

    @Nested
    class save {
        @Test
        void 닉네임을_변경하지_않고_게시글_등록() {
            // given
            String sameNickname = "닉네임";
            BoardCreateRequest request = new BoardCreateRequest("제목", "내용", sameNickname);
            Member member = mock(Member.class);

            when(member.getNickname()).thenReturn(sameNickname);

            // when
            boardWriteService.createBoard(request, member);

            // then
            ArgumentCaptor<Board> boardCaptor = ArgumentCaptor.forClass(Board.class);
            verify(boardRepository).save(boardCaptor.capture());

            Board capturedBoard = boardCaptor.getValue();
            assertThat(capturedBoard.getNickname()).isEqualTo(sameNickname);
            assertThat(capturedBoard.getIsAuthentic()).isTrue();
        }

        @Test
        void 닉네임을_변경하고_게시글을_등록() {
            // given
            String diffNickname = "다른닉네임";
            BoardCreateRequest request = new BoardCreateRequest("제목", "내용", "닉네임");
            Member member = mock(Member.class);

            when(member.getNickname()).thenReturn(diffNickname);

            // when
            boardWriteService.createBoard(request, member);

            // then
            ArgumentCaptor<Board> boardCaptor = ArgumentCaptor.forClass(Board.class);
            verify(boardRepository).save(boardCaptor.capture());

            Board capturedBoard = boardCaptor.getValue();
            assertThat(capturedBoard.getNickname()).isNotEqualTo(diffNickname);
            assertThat(capturedBoard.getIsAuthentic()).isFalse();
        }
    }

    @Nested
    class updateBoard {

        @Test
        void 게시글_내용_수정() {
            // given
            Long memberId = 1L;
            Member member = mock(Member.class);
            Board mockBoard = mock(Board.class);
            String newText = "새 글 내용";
            BoardUpdateRequest request = new BoardUpdateRequest(newText);

            when(member.getId()).thenReturn(memberId);
            when(mockBoard.getMemberId()).thenReturn(memberId);
            when(boardRepository.findById(eq(1L))).thenReturn(Optional.of(mockBoard));

            // when
            boardWriteService.updateBoard(request, member, memberId);

            // then
            verify(mockBoard).updateText(eq(newText));
        }

        @Test
        void 게시글_수정시_작성자가_아니면_Forbidden_예외_발생() {
            // given
            Long memberId = 1L;
            Member member = mock(Member.class);
            Board mockBoard = mock(Board.class);
            String newText = "새 글 내용";
            BoardUpdateRequest request = new BoardUpdateRequest(newText);

            when(member.getId()).thenReturn(memberId);
            when(mockBoard.getMemberId()).thenReturn(2L);
            when(boardRepository.findById(eq(1L))).thenReturn(Optional.of(mockBoard));

            // when
            assertThatThrownBy(() -> boardWriteService.updateBoard(request, member, memberId))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(ForbiddenException.forbidden().getMessage());
        }
    }
}
