package com.bookjob.comment.service;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.repository.CommentRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.member.annotation.MemberDataCleanup;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService {

    private final CommentRepository commentRepository;

    public void createComment(CommentCreateRequest request, Long boardId, Member member) {
        boolean isAuthentic = request.nickname().equals(member.getNickname());

        Comment comment = Comment.builder()
                .text(request.content())
                .boardId(boardId)
                .isAuthentic(isAuthentic)
                .memberId(member.getId())
                .nickname(request.nickname())
                .build();

        commentRepository.save(comment);
    }

    public void updateComment(CommentUpdateRequest request, Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundException::commentNotFound
        );

        if (!member.getId().equals(comment.getMemberId())) {
            throw ForbiddenException.commentForbidden();
        }

        comment.setText(request.content());
    }

    public void deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundException::commentNotFound
        );

        if (!member.getId().equals(comment.getMemberId())) {
            throw ForbiddenException.commentForbidden();
        }

        comment.delete();
    }

    @MemberDataCleanup
    public void deleteComment(Long memberId) {
        commentRepository.deleteAllByMemberId(memberId);
    }
}
