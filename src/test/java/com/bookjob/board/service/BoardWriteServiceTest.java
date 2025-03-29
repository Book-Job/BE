package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.member.domain.Member;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        void sameNickname_save() {
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
        void differentNickname_save() {
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

}
