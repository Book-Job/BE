package com.bookjob.comment.service;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.request.CommentRequest;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentWriteServiceTest {

    @InjectMocks
    private CommentWriteService commentWriteService;

    @Mock
    private CommentRepository commentRepository;

    @Nested
    class CreateComment{

        @Test
        void 자유게시판_게시글에_댓글을_생성한다(){
            // given
            Long boardId = 1L;
            Long memberId = 1L;
            String nickname = "test";
            CommentRequest request = new CommentRequest(
                    "새 댓글",
                    nickname,
                    "비밀번호"
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
}
