package com.bookjob.board.controller;

import com.bookjob.board.dto.request.BoardCreateRequest;
import com.bookjob.board.dto.request.BoardUpdateRequest;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.facade.BoardFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.request.BoardIdsRequest;
import com.bookjob.member.dto.response.MyPostingsInBoardResponse;
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
    public ResponseEntity<?> getBoardsAfterCursorWithKeyword(@RequestParam(required = false) Long last, @RequestParam(required = false) String keyword) {
        CursorBoardResponse response = boardFacade.getBoardsAfterCursorWithKeyword(keyword, last, pageSize);

        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@RequestBody BoardUpdateRequest request,
                                         @PathVariable Long boardId,
                                         @AuthenticationPrincipal(expression = "member") Member member) {
        boardFacade.updateBoard(request, member, boardId);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
                                         @AuthenticationPrincipal(expression = "member") Member member) {
        boardFacade.deleteBoard(boardId, member);

        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        BoardDetailResponse res = boardFacade.getBoardDetail(boardId);

        return ResponseEntity.ok(CommonResponse.success(res));
    }

    @GetMapping("/best")
    public ResponseEntity<?> getBoardBest() {
        return ResponseEntity.ok(CommonResponse.success(boardFacade.getBoardBest()));
    }

    /**
     * 내가 쓴 자유게시판 조회 및 선택 삭제
     */
    @GetMapping("/members")
    public ResponseEntity<?> getMyPostingsInBoard(@AuthenticationPrincipal(expression = "member") Member member,
                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int limit) {
        MyPostingsInBoardResponse myPostingsInBoardResponse = boardFacade.getMyPostingsInBoard(member, page, limit);
        return ResponseEntity.ok(CommonResponse.success(myPostingsInBoardResponse));
    }

    @DeleteMapping("/members")
    public ResponseEntity<?> deleteMyPostingsInBoard(@AuthenticationPrincipal(expression = "member") Member member,
                                                     @Valid @RequestBody BoardIdsRequest request) {
        boardFacade.deleteMyPostingsInBoard(member, request);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
