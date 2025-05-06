package com.bookjob.member.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record MyPostingsInBoardResponse(
        List<MyPostingsInBoard> myPostingsInBoardList,
        int currentPage,
        int limit,
        Boolean hasNext
) {
    public static MyPostingsInBoardResponse of (Page<MyPostingsInBoard> postings) {
        return new MyPostingsInBoardResponse(
                postings.getContent(),
                postings.getNumber(),
                postings.getSize(),
                postings.hasNext()
        );
    }
}
