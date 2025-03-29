package com.bookjob.board.controller;

import com.bookjob.board.dto.BoardCreateRequest;
import com.bookjob.board.facade.BoardFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardFacade boardFacade;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> createBoard(@Valid @RequestBody BoardCreateRequest request,
                                                            @AuthenticationPrincipal(expression = "member") Member member) {
        boardFacade.createBoard(request, member);

        return ResponseEntity.ok(CommonResponse.success());
    }
}
