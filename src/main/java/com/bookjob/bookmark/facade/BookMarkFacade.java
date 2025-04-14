package com.bookjob.bookmark.facade;

import com.bookjob.bookmark.dto.request.BookMarkCreateRequest;
import com.bookjob.bookmark.dto.response.MyBookMarksListResponse;
import com.bookjob.bookmark.service.BookMarkService;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMarkFacade {
    private final BookMarkService bookMarkService;

    public void createBookMark(Member member, BookMarkCreateRequest request) {
        bookMarkService.createBookMark(member, request);
    }

    public void deleteBookMark(Member member, Long bookMarkId) {
        bookMarkService.deleteBookMark(member, bookMarkId);
    }

    public MyBookMarksListResponse getMyBookMarks(Member member) {
        return bookMarkService.getMyBookmarks(member);
    }
}
