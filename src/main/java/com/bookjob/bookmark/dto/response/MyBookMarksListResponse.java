package com.bookjob.bookmark.dto.response;

import java.util.List;

public record MyBookMarksListResponse (
        List<MyBookMarksResponse> bookMarksResponses
) {
}
