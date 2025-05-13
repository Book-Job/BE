package com.bookjob.comment.service;

import com.bookjob.comment.dto.response.CommentResponse;
import com.bookjob.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentReadService {

    private final CommentRepository commentRepository;

    public List<CommentResponse> getCommentByBoardId(Long boardId, Long cursorId, int size, Long memberId) {
        if(cursorId == null || cursorId <= 1L) {
            cursorId = Long.MAX_VALUE;
        }

        return commentRepository.findCommentByBoardId(boardId, cursorId, size, memberId);
    }
}
