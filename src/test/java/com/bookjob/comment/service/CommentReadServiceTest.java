package com.bookjob.comment.service;

import com.bookjob.comment.dto.response.CommentResponse;
import com.bookjob.comment.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentReadServiceTest {

    @InjectMocks
    private CommentReadService commentReadService;

    @Mock
    private CommentRepository commentRepository;

    @Nested
    class GetCommentByBoardId {

        @Test
        void 게시판_PK를_이용하여_댓글을_20개_조회한다() {
            // given
            Long boardId = 1L;
            int size = 20;

            List<CommentResponse> commentResponses = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                commentResponses.add(mock(CommentResponse.class));
            }

            when(commentRepository.findCommentByBoardId(boardId, Long.MAX_VALUE, size)).thenReturn(commentResponses);

            // when
            List<CommentResponse> result = commentReadService.getCommentByBoardId(boardId, null, size);

            // then
            Assertions.assertThat(result).hasSize(size);
        }

        @Test
        void 커서가_음수일_때_게시판_PK를_이용하여_댓글을_20개_조회한다() {
            // given
            Long boardId = 1L;
            Long cursorId = -1L;
            int size = 20;

            List<CommentResponse> commentResponses = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                commentResponses.add(mock(CommentResponse.class));
            }

            when(commentRepository.findCommentByBoardId(boardId, Long.MAX_VALUE, size)).thenReturn(commentResponses);

            // when
            List<CommentResponse> result = commentReadService.getCommentByBoardId(boardId, cursorId, size);

            // then
            Assertions.assertThat(result).hasSize(size);
        }

        @Test
        void 커서_ID_이후부터_최대_20개의_댓글을_조회한다() {
            // given
            Long boardId = 1L;
            Long cursorId = 10L;
            int size = 20;
            int expectedResultCount = 10; // 커서 이후 댓글 수

            List<CommentResponse> expectedComments = new ArrayList<>();
            for (int i = 0; i < expectedResultCount; i++) {
                expectedComments.add(mock(CommentResponse.class));
            }

            when(commentRepository.findCommentByBoardId(boardId, cursorId, size)).thenReturn(expectedComments);

            // when
            List<CommentResponse> result = commentReadService.getCommentByBoardId(boardId, cursorId, size);

            // then
            Assertions.assertThat(result).hasSize(expectedResultCount);
        }
    }
}