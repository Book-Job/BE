package com.bookjob.job.repository;

import com.bookjob.job.domain.JobSeeking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekingRepository extends JpaRepository<JobSeeking, Long> {
}
