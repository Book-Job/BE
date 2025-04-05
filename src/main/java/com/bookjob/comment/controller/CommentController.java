package com.bookjob.comment.controller;

import com.bookjob.comment.dto.request.CommentCreateRequest;
import com.bookjob.comment.dto.request.CommentUpdateRequest;
import com.bookjob.comment.dto.response.CommentResponse;
import com.bookjob.comment.facade.CommentFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    private static final int COMMENT_SIZE = 20;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequest commentCreateRequest,
                                           @PathVariable("boardId") Long boardId,
                                           @AuthenticationPrincipal(expression = "member") Member member) {
        commentFacade.createComment(commentCreateRequest, boardId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequest request,
                                           @PathVariable("boardId") Long boardId,
                                           @PathVariable("commentId") Long commentId,
                                           @AuthenticationPrincipal(expression = "member") Member member) {
        commentFacade.updateComment(request, boardId, commentId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }


    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("boardId") Long boardId,
                                           @PathVariable("commentId") Long commentId,
                                           @AuthenticationPrincipal(expression = "member") Member member) {
        commentFacade.deleteComment(boardId, commentId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable("boardId") Long boardId,
                                         @RequestParam(value = "lastComment", required = false) Long lastCommentId) {
        List<CommentResponse> response = commentFacade.getComments(boardId, lastCommentId, COMMENT_SIZE);

        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
