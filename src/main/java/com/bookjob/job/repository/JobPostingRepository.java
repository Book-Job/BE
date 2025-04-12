package com.bookjob.job.repository;

import com.bookjob.job.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long>, JobPostingQueryRepository {
}
