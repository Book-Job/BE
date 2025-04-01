package com.bookjob.comment.controller;

import com.bookjob.comment.dto.request.CommentRequest;
import com.bookjob.comment.facade.CommentFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest,
                                           @PathVariable("boardId") Long boardId,
                                           @AuthenticationPrincipal(expression = "member") Member member) {
        commentFacade.createComment(commentRequest, boardId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }
}
