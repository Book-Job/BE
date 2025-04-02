package com.bookjob.comment.service;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.repository.CommentRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
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
                .content(request.content())
                .boardId(boardId)
                .isAuthentic(isAuthentic)
                .password(request.password())
                .memberId(member.getId())
                .nickname(request.nickname())
                .build();

        commentRepository.save(comment);
    }

    public void updateComment(CommentUpdateRequest request, Long commentId, Member member){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotFoundException::commentNotFound
        );

        if(comment.getIsAuthentic()){
            if(!member.getId().equals(comment.getMemberId())){
                throw ForbiddenException.commentForbidden();
            }
        }

        if(!comment.getPassword().equals(request.password())){
            throw ForbiddenException.commentForbidden();
        }

        comment.setContent(request.content());
    }
}
