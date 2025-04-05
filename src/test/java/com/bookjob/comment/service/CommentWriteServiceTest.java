package com.bookjob.comment.service;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.repository.CommentRepository;
import com.bookjob.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentWriteServiceTest {

    @InjectMocks
    private CommentWriteService commentWriteService;

    @Mock
    private CommentRepository commentRepository;

    @Nested
    class CreateComment {

        @Test
        void 자유게시판_게시글에_댓글을_생성한다() {
            // given
            Long boardId = 1L;
            Long memberId = 1L;
            String nickname = "test";
            CommentCreateRequest request = new CommentCreateRequest(
                    "새 댓글",
                    nickname
            );
            Member member = mock(Member.class);
            when(member.getId()).thenReturn(memberId);
            when(member.getNickname()).thenReturn(nickname);

            // when
            commentWriteService.createComment(request, boardId, member);

            // then
            ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
            verify(commentRepository).save(captor.capture());

            Assertions.assertThat(nickname).isEqualTo(captor.getValue().getNickname());
        }
    }

    @Nested
    class UpdateComment {

        @Test
        void 자유게시판_게시글에_생성된_댓글을_수정한다() {
            // given
            Long boardId = 1L;
            Long commentId = 1L;
            Long memberId = 1L;
            CommentUpdateRequest request = new CommentUpdateRequest("수정된 글");
            Member mockMember = mock(Member.class);
            Comment mockComment = spy(Comment.builder()
                    .nickname("nickname")
                    .isAuthentic(true)
                    .text("댓글")
                    .memberId(memberId)
                    .boardId(boardId)
                    .build());

            when(mockMember.getId()).thenReturn(memberId);
            when(commentRepository.findById(commentId)).thenReturn(Optional.of(mockComment));

            // when
            commentWriteService.updateComment(request, commentId, mockMember);

            // then
            verify(commentRepository).findById(commentId);
            verify(mockComment).setText(request.content());
            Assertions.assertThat(mockComment.getText()).isEqualTo(request.content());
        }
    }

    @Nested
    class DeleteComment {

        @Test
        void 자유게시판_게시글에_생성된_댓글을_삭제한다() {
            // given
            Long commentId = 1L;
            Long memberId = 1L;
            Member mockMember = mock(Member.class);
            Comment mockComment = mock(Comment.class);

            when(mockMember.getId()).thenReturn(memberId);
            when(mockComment.getMemberId()).thenReturn(memberId);
            when(commentRepository.findById(commentId)).thenReturn(Optional.of(mockComment));
            doNothing().when(commentRepository).delete(mockComment);

            // when
            commentWriteService.deleteComment(commentId, mockMember);

            // then
            ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
            verify(commentRepository).findById(commentId);
            verify(commentRepository).delete(captor.capture());
        }
    }
}
