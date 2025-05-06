package com.bookjob.job.dto.response;

import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import org.springframework.data.domain.Page;

import java.util.List;

public record MyPostingsInRecruitmentResponse(
        List<MyPostingsInRecruitment> postings,
        int currentPage,
        int limit,
        Boolean hasNext
) {

    public static MyPostingsInRecruitmentResponse of (Page<MyPostingsInRecruitment> postings) {
        return new MyPostingsInRecruitmentResponse(
                postings.getContent(),
                postings.getNumber(),
                postings.getSize(),
                postings.hasNext()
        );
    }
}
