package com.bookjob.comment.facade;

import com.bookjob.comment.dto.request.CommentRequest;
import com.bookjob.comment.service.CommentWriteService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentWriteService commentWriteService;

    public void createComment(CommentRequest commentRequest, Long boardId, Member member) {
        commentWriteService.createComment(commentRequest, boardId, member);
    }
}
