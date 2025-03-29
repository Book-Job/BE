package com.bookjob.board.controller;

import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.dto.CursorBoardResponse;
import com.bookjob.board.facade.BoardFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardFacade boardFacade;
    private final static int pageSize = 6;

    @PostMapping
    public ResponseEntity<?> createBoard(@Valid @RequestBody BoardCreateRequest request,
                                         @AuthenticationPrincipal(expression = "member") Member member) {
        boardFacade.createBoard(request, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping
    public ResponseEntity<?> getBoardsAfterCursor(@RequestParam(required = false) Long cursor) {
        CursorBoardResponse response = boardFacade.getBoardsAfterCursor(cursor, pageSize);

        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
