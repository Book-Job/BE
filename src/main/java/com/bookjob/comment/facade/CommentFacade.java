package com.bookjob.comment.facade;

import com.bookjob.board.service.BoardReadService;
import com.bookjob.board.service.BoardWriteService;
import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.dto.response.CommentResponse;
import com.bookjob.comment.service.CommentReadService;
import com.bookjob.comment.service.CommentWriteService;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentWriteService commentWriteService;
    private final CommentReadService commentReadService;
    private final BoardReadService boardReadService;
    private final BoardWriteService boardWriteService;

    public void createComment(CommentCreateRequest commentCreateRequest, Long boardId, Member member) {
        if (boardReadService.notExistsBoard(boardId)) {
            throw NotFoundException.boardNotFound(boardId);
        }

        boardWriteService.increaseCommentCount(boardId);
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

        boardWriteService.decreaseCommentCount(boardId);
        commentWriteService.deleteComment(commentId, member);
    }

    public List<CommentResponse> getComments(Long boardId, Long lastComment, int size, Long memberId) {
        if (boardReadService.notExistsBoard(boardId)) {
            throw NotFoundException.boardNotFound(boardId);
        }

        return commentReadService.getCommentByBoardId(boardId, lastComment, size, memberId);
    }
}
