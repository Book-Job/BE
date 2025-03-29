package com.bookjob.board.service;

import com.bookjob.board.dto.BoardPreviewResponse;
import com.bookjob.board.dto.CursorBoardResponse;
import com.bookjob.board.repository.BoardRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardReadServiceTest {

    @InjectMocks
    private BoardReadService boardReadService;

    @Mock
    private BoardRepository boardRepository;

    @Nested
    class GetBoardsAfterCursor {

        @Test
        void 게시판_글_전체_조회_쿼리_파라메터_없음() {
            // given
            int pageSize = 3;
            BoardPreviewResponse board1 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse board2 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse lastBoard = mock(BoardPreviewResponse.class);

            when(lastBoard.boardId()).thenReturn(1L);

            Pageable pageable = PageRequest.of(0, pageSize);

            List<BoardPreviewResponse> boards = List.of(board1, board2, lastBoard);
            when(boardRepository.findBoardsBeforeCursor(any(Long.class), eq(pageable))).thenReturn(boards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursor(null, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).hasSize(pageSize);
            assertThat(response.boards()).containsExactly(board1, board2, lastBoard);
            assertThat(response.lastBoardId()).isEqualTo(1L);

            verify(boardRepository).findBoardsBeforeCursor(eq(Long.MAX_VALUE), eq(pageable));
        }

        @Test
        void should_return_boards_after_specific_cursor() {
            // given
            Long cursor = 5L;
            int pageSize = 2;
            BoardPreviewResponse board1 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse board2 = mock(BoardPreviewResponse.class);

            when(board2.boardId()).thenReturn(3L);

            List<BoardPreviewResponse> boards = List.of(board1, board2);
            Pageable pageable = PageRequest.of(0, pageSize);

            when(boardRepository.findBoardsBeforeCursor(eq(cursor), eq(pageable))).thenReturn(boards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursor(cursor, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).hasSize(pageSize);
            assertThat(response.boards()).containsExactly(board1, board2);
            assertThat(response.lastBoardId()).isEqualTo(3L);

            verify(boardRepository).findBoardsBeforeCursor(eq(cursor), eq(pageable));
        }

        @Test
        void should_return_empty_response_when_no_more_boards() {
            // given
            Long cursor = 2L;
            int pageSize = 10;
            List<BoardPreviewResponse> emptyBoards = List.of();
            Pageable pageable = PageRequest.of(0, pageSize);

            when(boardRepository.findBoardsBeforeCursor(eq(cursor), eq(pageable))).thenReturn(emptyBoards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursor(cursor, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).isEmpty();
            assertThat(response.lastBoardId()).isEqualTo(0L);

            verify(boardRepository).findBoardsBeforeCursor(eq(cursor), eq(pageable));
        }
    }
}
