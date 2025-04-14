package com.bookjob.bookmark.controller;

import com.bookjob.bookmark.dto.request.BookMarkCreateRequest;
import com.bookjob.bookmark.dto.response.MyBookMarksListResponse;
import com.bookjob.bookmark.facade.BookMarkFacade;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookMarkController {

    private final BookMarkFacade bookMarkFacade;

    @PostMapping
    ResponseEntity<?> createBookMark(@AuthenticationPrincipal(expression = "member") Member member,
                                     @RequestBody BookMarkCreateRequest request) {
        bookMarkFacade.createBookMark(member, request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping("{bookMarkId}")
    ResponseEntity<?> deleteBookMark(@AuthenticationPrincipal(expression = "member") Member member,
                                     @PathVariable Long bookMarkId) {
        bookMarkFacade.deleteBookMark(member, bookMarkId);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping
    ResponseEntity<?> getMyBookMarks(@AuthenticationPrincipal(expression = "member") Member member) {
        MyBookMarksListResponse response = bookMarkFacade.getMyBookMarks(member);
        return ResponseEntity.ok(CommonResponse.success(response));
    }

}
