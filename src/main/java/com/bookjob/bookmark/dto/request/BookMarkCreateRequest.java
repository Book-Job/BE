package com.bookjob.bookmark.dto.request;

public record BookMarkCreateRequest(
        Long relatedId,
        String type
) {
}
