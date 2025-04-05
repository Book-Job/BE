package com.bookjob.comment.facade;

import com.bookjob.board.service.BoardReadService;
import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.service.CommentWriteService;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentWriteService commentWriteService;
    private final BoardReadService boardReadService;

    public void createComment(CommentCreateRequest commentCreateRequest, Long boardId, Member member) {
        if (boardReadService.notExistsBoard(boardId)) {
            throw NotFoundException.boardNotFound(boardId);
        }

        commentWriteService.createComment(commentCreateRequest, boardId, member);
    }

    public void updateComment(CommentUpdateRequest commentUpdateRequest, Long boardId, Long commentId, Member member) {
        if (boardReadService.notExistsBoard(boardId)) {
            throw NotFoundException.boardNotFound(boardId);
        }

        commentWriteService.updateComment(commentUpdateRequest, commentId, member);
    }

    public void deleteComment(Long boardId, Long commentId, Member member) {
        if (boardReadService.notExistsBoard(boardId)) {
            throw NotFoundException.boardNotFound(boardId);
        }

        commentWriteService.deleteComment(commentId, member);
    }
}
