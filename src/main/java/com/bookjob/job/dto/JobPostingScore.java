package com.bookjob.job.dto;

import com.bookjob.job.domain.JobPosting;

public record JobPostingScore (
        JobPosting jobPosting,
        Double score
) {
}
