package com.bookjob.job.dto.response;

import com.bookjob.job.dto.application.MyPostingsInRecruitment;

import java.util.List;

public record MyPostingsInRecruitmentResponse(
        List<MyPostingsInRecruitment> postings
) {
}
