package com.bookjob.member.dto.response;

import com.bookjob.member.dto.MyPostingsInBoard;

import java.util.List;

public record MyPostingsInBoardResponse(
        List<MyPostingsInBoard> myPostingsInBoardList
) {
}
