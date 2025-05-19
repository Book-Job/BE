package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.BoardPreviewResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.repository.BoardQueryRepository;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.NotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardReadServiceTest {

    @InjectMocks
    private BoardReadService boardReadService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardQueryRepository boardQueryRepository;

    @Nested
    class GetBoardsAfterCursorWithKeyword {

        @Test
        void 게시판_글_전체_조회_쿼리_파라메터_없음() {
            // given
            int pageSize = 3;
            BoardPreviewResponse board1 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse board2 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse lastBoard = mock(BoardPreviewResponse.class);

            when(lastBoard.boardId()).thenReturn(1L);

            List<BoardPreviewResponse> boards = List.of(board1, board2, lastBoard);
            when(boardQueryRepository.getBoardListWithKeyword(isNull(), any(Long.class), eq(pageSize))).thenReturn(boards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursorWithKeyword(null, null, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).hasSize(pageSize);
            assertThat(response.boards()).containsExactly(board1, board2, lastBoard);
            assertThat(response.lastBoardId()).isEqualTo(1L);

            verify(boardQueryRepository).getBoardListWithKeyword(isNull(), eq(Long.MAX_VALUE), eq(pageSize));
        }

        @Test
        void 커서가_있을_때_글_전체_조회() {
            // given
            Long cursor = 5L;
            int pageSize = 2;
            BoardPreviewResponse board1 = mock(BoardPreviewResponse.class);
            BoardPreviewResponse board2 = mock(BoardPreviewResponse.class);

            when(board2.boardId()).thenReturn(3L);

            List<BoardPreviewResponse> boards = List.of(board1, board2);

            when(boardQueryRepository.getBoardListWithKeyword(isNull(), eq(cursor), eq(pageSize))).thenReturn(boards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursorWithKeyword(null, cursor, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).hasSize(pageSize);
            assertThat(response.boards()).containsExactly(board1, board2);
            assertThat(response.lastBoardId()).isEqualTo(3L);

            verify(boardQueryRepository).getBoardListWithKeyword(isNull(), eq(cursor), eq(pageSize));
        }

        @Test
        void 글이_존재하지_않을_때_빈_리스트_반환() {
            // given
            Long cursor = 2L;
            int pageSize = 10;
            List<BoardPreviewResponse> emptyBoards = List.of();

            when(boardQueryRepository.getBoardListWithKeyword(isNull(), eq(cursor), eq(pageSize))).thenReturn(emptyBoards);

            // when
            CursorBoardResponse response = boardReadService.getBoardsAfterCursorWithKeyword(null, cursor, pageSize);

            // then
            assertThat(response).isNotNull();
            assertThat(response.boards()).isEmpty();
            assertThat(response.lastBoardId()).isEqualTo(0L);

            verify(boardQueryRepository).getBoardListWithKeyword(isNull(), eq(cursor), eq(pageSize));
        }
    }

    @Nested
    class GetBoardDetails {

        @Test
        void 게시글_PK_로_상세_조회() {
            // given
            Long boardId = 1L;
            Long memberId = 2L;

            Board board = spy(boardFixture());
            when(board.getMemberId()).thenReturn(3L);

            when(boardRepository.findBoardByIdAndDeletedAtIsNull(boardId)).thenReturn(Optional.of(board));

            // when
            BoardDetailResponse response = boardReadService.getBoardDetails(boardId, memberId);

            // then
            verify(boardRepository).findBoardByIdAndDeletedAtIsNull(boardId);
            verify(board).increaseViewCount();

            assertThat(response.title()).isEqualTo(board.getTitle());
            assertThat(response.text()).isEqualTo(board.getText());
            assertThat(response.nickname()).isEqualTo(board.getNickname());
            assertThat(response.isAuthentic()).isEqualTo(board.getIsAuthentic());
            assertThat(response.isWriter()).isFalse();
            assertThat(response.createdAt()).isEqualTo(board.getCreatedAt());
            assertThat(response.modifiedAt()).isEqualTo(board.getModifiedAt());
        }

        @Test
        void 글이_존재하지_않을_때_NOTFOUND_반환() {
            // given
            Long wrongBoardId = 1L;
            Long memberId = 2L;

            when(boardRepository.findBoardByIdAndDeletedAtIsNull(wrongBoardId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> boardReadService.getBoardDetails(wrongBoardId, memberId))
                    .isInstanceOf(NotFoundException.class);

            verify(boardRepository).findBoardByIdAndDeletedAtIsNull(wrongBoardId);
        }
    }

    private Board boardFixture() {
        return Board.builder()
                .isAuthentic(true)
                .memberId(1L)
                .nickname("테스트유저")
                .text("테스트 본문입니다. 게시글 내용이 여기에 들어갑니다.")
                .title("테스트 게시글입니다")
                .build();
    }
}
