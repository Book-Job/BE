package com.bookjob.comment.service;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.request.CommentRequest;
import com.bookjob.comment.repository.CommentRepository;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService {

    private final CommentRepository commentRepository;

    public void createComment(CommentRequest request, Long boardId, Member member) {
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
}
