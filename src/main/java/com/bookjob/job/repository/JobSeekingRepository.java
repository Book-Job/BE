package com.bookjob.job.repository;

import com.bookjob.job.domain.JobSeeking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekingRepository extends JpaRepository<JobSeeking, Long>, JobSeekingQueryRepository {
    void deleteAllByMemberId(Long memberId);

    Optional<JobSeeking> findByIdAndDeletedAtIsNull(Long id);
}
