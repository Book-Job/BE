package com.bookjob.job.dto.response;

import com.bookjob.job.domain.JobPosting;
import lombok.Getter;

@Getter
public class JobPostingBestResponse {
    private final Long jobPostingId;
    private final String title;
    private final Integer viewCount;

    public JobPostingBestResponse(JobPosting jobPosting) {
        this.jobPostingId = jobPosting.getId();
        this.title = jobPosting.getTitle();
        this.viewCount = (jobPosting.getViewCount() != null) ? jobPosting.getViewCount() : 0;
    }
}
