package com.bookjob.member.dto;

import java.util.List;

public record MyPostingsInBoardResponse(
        List<MyPostingsInBoard> myPostingsInBoardList
) {
}
