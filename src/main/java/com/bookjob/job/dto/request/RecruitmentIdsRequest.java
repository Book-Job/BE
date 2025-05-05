package com.bookjob.job.dto.request;

import java.util.List;

public record RecruitmentIdsRequest (
        List<RecruitmentDeleteRequest> deleteRequest
) {
}
